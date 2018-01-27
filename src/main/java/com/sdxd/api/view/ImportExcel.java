package com.sdxd.api.view;

import com.sdxd.common.web.util.ClassUtil;
import com.sdxd.common.web.util.spreadsheet.Spreadsheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.view
 * 系统名           ：excel文件导入
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/8    wenzhou.xu              Created
 */
public class ImportExcel extends Spreadsheet {

    private static final Logger log = LoggerFactory.getLogger(ImportExcel.class);

    public ImportExcel(InputStream in, Type type) {
        super(in, type);
    }

    public ImportExcel(Workbook workbook) {
        super(workbook);
    }

    public Integer getNumberOfSheet(){
        return getWorkbook().getNumberOfSheets();
    }

    public Integer get(Integer sheetIndex){
        return getWorkbook().getSheetAt(sheetIndex).getFirstRowNum();
    }

    public Integer getLastRowNum(Integer sheetIndex){
        return getWorkbook().getSheetAt(sheetIndex).getPhysicalNumberOfRows();
    }

    public Short getLastColumnNum(Integer sheetIndex, Integer rowIndex){
        return getWorkbook().getSheetAt(sheetIndex).getRow(rowIndex).getLastCellNum();
    }

    public String getStringValue(Integer sheetIndex, Integer rowIndex, Short columnIndex){
        return getStringValue(getWorkbook().getSheetAt(sheetIndex), rowIndex, columnIndex);
    }

    public <T>List<T> getTemplates(T t) {
        List<T> list = new ArrayList<>();
        Short columnNum = 0;
        List<Field> fieldList = ClassUtil.getPrivateFieldList(t.getClass());
        for(int sheetIndex = 0; sheetIndex < this.getNumberOfSheet(); sheetIndex++){
            for(int rowIndex = 0; rowIndex < this.getLastRowNum(sheetIndex); rowIndex++){
                //跳过标题，默认标题为第一行
                //列长度可以从标题数决定
                if(rowIndex == 0){
                    columnNum = this.getLastColumnNum(sheetIndex, rowIndex);
                    continue;
                }
                //操作其他行
                for(short columnIndex = 0; columnIndex < columnNum; columnIndex++){
                    try {
                        ClassUtil.setField(t, fieldList.get(columnIndex), this.getStringValue(sheetIndex, rowIndex, columnIndex));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                list.add(t);
            }
        }
        return list;
    }
}
