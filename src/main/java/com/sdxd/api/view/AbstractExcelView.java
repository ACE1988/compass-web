package com.sdxd.api.view;

import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.util.ClassUtil;
import com.sdxd.common.web.util.spreadsheet.RowBuilder;
import com.sdxd.common.web.util.spreadsheet.SheetBuilder;
import com.sdxd.common.web.util.spreadsheet.Spreadsheet;
import com.sdxd.common.web.vo.RestResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.sdxd.common.web.util.Throwables.throwProcessBizException;

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
public abstract class AbstractExcelView<T> extends AbstractXlsxView {

    //@VIEW 注解 model强制配置成MODEL
    private static final String MODEL = "model";

    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    private String fileName;

    private String sheetName;

    private String[] titles;

    private CellStyle headStyle;

    private CellStyle bodyStyle;

    public AbstractExcelView(){
        this.fileName = getFileName();
        this.sheetName = getSheetName();
        this.titles = getTitles();
    }

    @Override
    protected void buildExcelDocument(
            Map<String, Object> model, Workbook workbook,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setHeader(CONTENT_DISPOSITION, getContentDisposition());
        SheetBuilder sheet = getSheetBuilder(workbook);
        sheet.newRow(headStyle).cells(titles);
        if (!model.containsKey(MODEL)) {
            return;
        }
        RestResponse<T> restResponse = (RestResponse<T>) model.get(MODEL);
        //noinspection unchecked
        List<T> list = (List<T>)restResponse.getContent();
        if(list == null)
            return;
        try {
            list.forEach(t -> {
                RowBuilder rowBuilder = sheet.newRow(bodyStyle);
                List<Field> fieldList = ClassUtil.getPrivateFieldList(t.getClass());
                for(Field field : fieldList){
                    Object obj = ClassUtil.getField(t, field);
                    rowBuilder.cell(obj.toString());
                }
            });
        } catch (Exception e) {
            throwProcessBizException(e);
        }
    }

    /**
     *
     * @param workbook
     * @return
     */
    private SheetBuilder getSheetBuilder(Workbook workbook) {
        Spreadsheet spreadsheet = new Spreadsheet(workbook);
        //获取字体格式
        Font bodyFont = getFont(spreadsheet);
        //设置头格式
        headStyle = getHeadStyle(spreadsheet, bodyFont);
        //设置内容格式
        bodyStyle = getBodyStyle(spreadsheet, bodyFont);

        return spreadsheet.newSheet(sheetName);
    }

    private String getContentDisposition() {
        return String.format("attachment; filename=" + fileName + "-%s.xlsx", BillNoUtils.GenerateBillNo());
    }

    protected abstract Font getFont(Spreadsheet spreadsheet);
    protected abstract CellStyle getBodyStyle(Spreadsheet spreadsheet, Font bodyFont);
    protected abstract CellStyle getHeadStyle(Spreadsheet spreadsheet, Font bodyFont);
    protected abstract String getFileName();
    protected abstract String getSheetName();
    protected abstract String[] getTitles();

}
