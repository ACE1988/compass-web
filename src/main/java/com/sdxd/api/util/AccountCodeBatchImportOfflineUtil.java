package com.sdxd.api.util;

import com.alibaba.fastjson.JSON;
import com.sdxd.api.vo.credit.AccountCodeDetail;
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

/**
 * Created by lenovo on 2017/7/21.
 */
public class AccountCodeBatchImportOfflineUtil extends Spreadsheet {
    private Logger logger = LoggerFactory.getLogger(AccountCodeBatchImportOfflineUtil.class);

    public AccountCodeBatchImportOfflineUtil(InputStream in, String type) {
        super(in, Type.forType(type));
    }

    public List<AccountCodeDetail> parse() {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<AccountCodeDetail> list = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() < 1) continue; //从第二行开始读数据
            list.add(getData(row));
        }
        logger.info("【信审系统】==========》用户码批量上传，上传文件成功" + JSON.toJSON(list));

        return list;
    }

    private AccountCodeDetail getData(Row row) {
        AccountCodeDetail accountCodeDetail = new AccountCodeDetail();
        String id = String.valueOf(getValue(row, 0)).trim();//获取第一个单元格内容
        String phone = String.valueOf(getValue(row, 1)).trim();
        String name = String.valueOf(getValue(row, 2)).trim();
        String accountCode = String.valueOf(getValue(row, 3)).trim();
        String annotation = String.valueOf(getValue(row, 4)).trim();
        String priority = String.valueOf(getValue(row, 5)).trim();
        //注入
        if ("null".equals(phone) || StringUtils.isBlank(phone)) {
            //如果phone为blank
            accountCodeDetail.setPhone("");
        } else {
            accountCodeDetail.setPhone(new BigDecimal(phone).toPlainString());
        }
        if ("null".equals(name)|| StringUtils.isBlank(name)) {
            accountCodeDetail.setName("");
        } else {
            accountCodeDetail.setName(name);
        }
        if ("null".equals(accountCode)|| StringUtils.isBlank(accountCode)) {
            accountCodeDetail.setAccountCode("");
        } else {
            accountCodeDetail.setAccountCode(accountCode);
        }
        if ("null".equals(priority)|| StringUtils.isBlank(priority)) {
            accountCodeDetail.setPriority("");
        } else {
            accountCodeDetail.setPriority(String.valueOf(new BigDecimal(priority).intValue()));
        }
        return accountCodeDetail;
    }


}
