package com.sdxd.api.util;

import com.alibaba.fastjson.JSON;
import com.sdxd.common.utils.DateUtils;
import com.sdxd.common.web.util.spreadsheet.Spreadsheet;
import com.sdxd.repayment.dubbo.request.OfflineBankDocument;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author liujie
 * @Date 2017/6/27
 * 盛大小贷 解析对公管理上传文件
 */
public class RepaymentOfflineSheetUtil extends Spreadsheet {

    private Logger logger = LoggerFactory.getLogger(RepaymentOfflineSheetUtil.class);

    public RepaymentOfflineSheetUtil(Workbook workbook) {
        super(workbook);
    }


    public RepaymentOfflineSheetUtil(InputStream in, String type) {
        super(in, Type.forType(type));
    }

    public List<OfflineBankDocument> parse(String accountId, String accountName, String bankName, String acceptCardNo) {
        Workbook workbook = getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<OfflineBankDocument> list = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() < 4) continue; //从第五行开始读数据
            OfflineBankDocument document = new OfflineBankDocument();
            DecimalFormat df = new DecimalFormat("0");
            Object obj = getValue(row.getCell(0));
            String date = String.valueOf(obj);
            String dateFormat = date.trim();//日期
            Object timeObj = getValue(row.getCell(1)); //时间

            String timeFormat = String.valueOf(timeObj).trim().length() < 6
                    ? 0 + String.valueOf(timeObj)
                    : String.valueOf(timeObj);
            //主键 替换9
            String docCode = dateFormat + timeFormat + String.valueOf(getValue(row.getCell(5))).trim().replace(".", "9");
            BigDecimal amount = new BigDecimal(getValue(row.getCell(4)).toString().trim()); //还款金额
            Date actualRepaymentTime = DateUtils.convert(dateFormat + timeFormat, "yyyyMMddHHmmss");//日期
            String voucherNo = String.valueOf(row.getCell(3));//凭证号
            String cardNo = String.valueOf(getValue(row.getCell(6)));//卡号
            String cardName = String.valueOf(getValue(row.getCell(7)));//持卡人姓名
            String abstractComment = String.valueOf(getValue(row.getCell(8)));//摘要
            String remarks = String.valueOf(getValue(row.getCell(9)));//备注

            document.setDocCode(docCode);
            document.setAmount(amount);
            document.setActualRepaymentTime(actualRepaymentTime);
            document.setVoucherNo(voucherNo);
            document.setCardNo(cardNo);
            document.setCardName(cardName);
            document.setAbstractComment(abstractComment);
            document.setRemarks(remarks);
            document.setImportId(accountId);
            document.setImportName(accountName);
            document.setChannel(bankName);
            document.setAcceptCardNo(acceptCardNo);
            list.add(document);
        }
        logger.info("【财务系统】==========》对公管理，上传文件成功" + JSON.toJSON(list));
        return list;
    }
}
