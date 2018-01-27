package com.sdxd.api.view;

import com.sdxd.api.vo.customer.EventStatus;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.util.Pages;
import com.sdxd.common.web.util.spreadsheet.SheetBuilder;
import com.sdxd.common.web.util.spreadsheet.Spreadsheet;
import com.sdxd.common.web.vo.PagingResponse;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.customer.dubbo.api.response.CustomerEventsInfo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import rx.Observable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.sdxd.common.web.util.Throwables.getCause;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.view
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/11     melvin                 Created
 */
public class CustomerEventView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(
            Map<String, Object> model, Workbook workbook,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setHeader("Content-Disposition",
                String.format("attachment; filename=\"events-%s.xlsx\"", BillNoUtils.GenerateBillNo()));

        Spreadsheet spreadsheet = new Spreadsheet(workbook);
        Font bodyFont = spreadsheet.newFont(font -> {
            font.setFontName("微软雅黑");
            font.setFontHeightInPoints((short) 10);
        });
        short dateFormat = spreadsheet.newDataFormat("yyyy-mm-dd hh:mm:ss");
        CellStyle headStyle = spreadsheet.newStyle(cellStyle -> {
            XSSFColor foregroundColor = spreadsheet.newColor(242, 242, 242);
            ((XSSFCellStyle) cellStyle).setFillForegroundColor(foregroundColor);
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellStyle.setFont(bodyFont);
            cellStyle.setWrapText(true);
        });
        CellStyle bodyStyle = spreadsheet.newStyle(cellStyle -> {
            cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle.setFont(bodyFont);
        });
        CellStyle dateStyle = spreadsheet.newStyle(cellStyle -> {
            cellStyle.setDataFormat(dateFormat);
            cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle.setFont(bodyFont);
        });

        SheetBuilder sheet = spreadsheet.newSheet("客户事件");
        sheet.newRow(headStyle).cells("序号", "来电时间", "客户编号", "事件编号", "来电事由", "坐席人员", "状态", "详细内容");

        Object events = model.get("events");
        if (events == null) {
            return;
        }
        //noinspection unchecked
        PagingResponse<CustomerEventsInfo> paging = PagingResponse.class.cast(events);
        Pages<CustomerEventsInfo> pages = paging.getPages();
        if (pages == null) {
            return;
        }

        try {
            Observable.from(pages).forEach(event -> {
                sheet.newRow(bodyStyle).
                        digit(pages.getCurrentIndex()).
                        date(event.getAnswerTime(), dateStyle).
                        cell(event.getUserId()).
                        cell(event.getId()).
                        cell(event.getCallMatter()).
                        cell(event.getCustomerName()).
                        cell(EventStatus.forStatusValue(event.getStatus())).
                        cell(event.getDetailedContent());
            });
        } catch (RuntimeException e) {
            //noinspection ThrowableResultOfMethodCallIgnored
            ProcessBizException processBizException = getCause(e, ProcessBizException.class);
            if (processBizException != null) {
                List<String> errorMessages = processBizException.getErrorMessages();
                Observable.from(errorMessages).forEach(sheet::addError);
            }
        }
    }
}
