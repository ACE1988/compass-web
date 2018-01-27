package com.sdxd.api.util;

import com.alibaba.fastjson.JSON;
import com.sdxd.common.web.util.spreadsheet.Spreadsheet;
import com.sdxd.repayment.dubbo.request.OfflineBankDocument;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by matteo on 2017/7/13.
 * 盛大小贷 解析对私管理上传文件
 */
public class RepaymentOfflinePrivateUtil extends Spreadsheet {


    private Logger logger = LoggerFactory.getLogger(RepaymentOfflinePrivateUtil.class);

    public RepaymentOfflinePrivateUtil(Workbook workbook) {
        super(workbook);
    }


    public RepaymentOfflinePrivateUtil(InputStream in, String type) {
        super(in, Type.forType(type));
    }

    public List<OfflineBankDocument> privateParse(String accountId, String accountName, String bankName, String acceptCardNo) throws ParseException {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<OfflineBankDocument> list = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() < 2) continue; //从第二行开始读数据
            list.add(getData(row, accountId, accountName, bankName, acceptCardNo));
        }
        logger.info("【财务系统】==========》对私管理，上传文件成功" + JSON.toJSON(list));
        return list;
    }

    private OfflineBankDocument getData
            (Row row, String accountId, String accountName, String bankName, String acceptCardNo)
            throws ParseException {
        OfflineBankDocument document = new OfflineBankDocument();
        String date = String.valueOf(row.getCell(0));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date actualRepaymentTime = format.parse(date);
        String cardName = String.valueOf(row.getCell(1));
        String cardNo = String.valueOf(row.getCell(2));
        BigDecimal amount = new BigDecimal(getValue(row.getCell(4)).toString().trim());
        String abstractComment = String.valueOf(row.getCell(6));
        String remarks = String.valueOf(row.getCell(7));
        String tradeCode = String.valueOf(row.getCell(8));
        String timeFormat = String.valueOf(date).trim().substring(11).replace(":", "");
        String dateFormat = String.valueOf(date).trim().substring(0, 9).replace("-", "");
        //主键 替换9
        String docCode = dateFormat + timeFormat + String.valueOf(getValue(row.getCell(5))).trim().replace(".", "9");
        document.setDocCode(docCode);
        document.setActualRepaymentTime(actualRepaymentTime);
        document.setCardName(cardName);
        document.setCardNo(cardNo);
        document.setAmount(amount);
        document.setAbstractComment(abstractComment);
        document.setRemarks(remarks);
        document.setTradeCode(tradeCode);
        document.setImportId(accountId);
        document.setImportName(accountName);
        document.setChannel(bankName);
        document.setAcceptCardNo(acceptCardNo);
        return document;
    }
}
