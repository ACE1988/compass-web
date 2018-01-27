package com.sdxd.api.view;

import com.sdxd.approve.dubbo.api.response.CreditDetailResponse;
import com.sdxd.common.web.util.spreadsheet.Spreadsheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

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
 * 2017/8/11    wenzhou.xu              Created
 */
public class CreditCheckFailView extends AbstractExcelView<CreditDetailResponse> {

    @Override
    protected String getFileName() {
        return "CreditCheckFail";
    }

    @Override
    protected String getSheetName() {
        return "征信失败";
    }

    @Override
    protected String[] getTitles(){
        return new String[]{"用户ID","电话号码","姓名","失败原因","征信源"};
    }

    @Override
    protected CellStyle getHeadStyle(Spreadsheet spreadsheet, Font bodyFont) {
        return spreadsheet.newStyle(cellStyle -> {
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
    }

    @Override
    protected CellStyle getBodyStyle(Spreadsheet spreadsheet, Font bodyFont) {
        return spreadsheet.newStyle(cellStyle -> {
            cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle.setFont(bodyFont);
        });
    }

    @Override
    protected Font getFont(Spreadsheet spreadsheet) {
        return spreadsheet.newFont(font -> {
            font.setFontName("微软雅黑");
            font.setFontHeightInPoints((short) 10);
        });
    }
}
