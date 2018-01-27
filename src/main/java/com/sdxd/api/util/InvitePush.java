package com.sdxd.api.util;

import com.sdxd.common.web.util.spreadsheet.Spreadsheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by matteo on 2017/8/3.
 * 消息推送的excel解析
 */


public class InvitePush extends Spreadsheet {

    public InvitePush(InputStream in, String type) {
        super(in, Type.forType(type));
    }

    public List<String> parse() {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<String> list = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() < 1) continue; //从第2行开始读数据
            list.add(getStringValue2(row, 0).toString());
        }

        return list;
    }
}
