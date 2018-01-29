
package com.sdxd.api.util;

import com.alibaba.fastjson.JSON;
import com.sdxd.api.vo.credit.IncreaseLineHistoryDetail;
import com.sdxd.common.web.util.spreadsheet.Spreadsheet;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.sdxd.common.web.trace.HttpTracer.DEBUG;

/**
 * Created by lenovo on 2017/7/21.
 */

public class IncreaseLineHistoryBatchImportOfflineUtil extends Spreadsheet {
    private Logger logger = LoggerFactory.getLogger(IncreaseLineHistoryBatchImportOfflineUtil.class);

    public IncreaseLineHistoryBatchImportOfflineUtil(InputStream in, String type) {
        super(in, Type.forType(type));
    }

    public List<IncreaseLineHistoryDetail> parse() {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<IncreaseLineHistoryDetail> list = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() < 1) continue; //从第二行开始读数据
            list.add(getData(row));
        }
        DEBUG(logger, "【信审系统】==========》提额批量上传，上传文件成功: {}", JSON.toJSON(list));

        return list;
    }

    private IncreaseLineHistoryDetail getData(Row row) {
        IncreaseLineHistoryDetail increaseLineHistoryDetail = new IncreaseLineHistoryDetail();

        String name = String.valueOf(getValue(row, 1)).trim();
        String phone = String.valueOf(getValue(row, 2)).trim();
        String currentCreditLine = String.valueOf(getValue(row, 3)).trim();
        String targetCreditLine = String.valueOf(getValue(row, 4)).trim();

        //注入
        if ("null".equals(phone)|| StringUtils.isBlank(phone)) {
            increaseLineHistoryDetail.setPhone("");
        } else {
            increaseLineHistoryDetail.setPhone(new BigDecimal(phone).toPlainString());
        }
        if ("null".equals(name)|| StringUtils.isBlank(name)) {
            increaseLineHistoryDetail.setName("");
        } else {
            increaseLineHistoryDetail.setName(name);
        }
        if ("null".equals(currentCreditLine)|| StringUtils.isBlank(currentCreditLine)) {
            increaseLineHistoryDetail.setCurrentCreditLine("");
        } else {
            increaseLineHistoryDetail.
                    setCurrentCreditLine(String.valueOf(new BigDecimal(currentCreditLine).intValue()));
        }
        if ("null".equals(targetCreditLine)|| StringUtils.isBlank(targetCreditLine)) {
            increaseLineHistoryDetail.setTargetCreditLine("");
        } else {
            increaseLineHistoryDetail.
                    setTargetCreditLine(String.valueOf(new BigDecimal(targetCreditLine).intValue()));
        }
        return increaseLineHistoryDetail;
    }


}
