package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liujie
 * @Date 2017/5/10
 * 盛大小贷
 */
public class ApproveQuery extends RestRequest {

    @ApiParam(value = "退款单id")
    @QueryParam(value = "id")
    private String id ;

    @ApiParam(value = "退款渠道")
    @QueryParam(value = "channel_code")
    private String channelCode ;

    @ApiParam(value = "扣款金额")
    @QueryParam(value = "document_amount")
    private BigDecimal documentAmount ;

    @ApiParam(value = "待核销金额")
    @QueryParam(value = "left_amount")
    private BigDecimal leftAmount ;

    @ApiParam(value = "退款金额")
    @QueryParam(value = "amount")
    private BigDecimal amount ;

    @ApiParam(
            value = "银行 例如 ：农业银行")
    @QueryParam(value = "bank_code")
    private String bankCode ;//银行


    @ApiParam(
            value = "银行卡号")
    @QueryParam(value = "card_no")
    private String cardNo ;//银行

    @ApiParam(
            value = "银行")
    @QueryParam(value = "bank_name")
    private String bankName ;//银行

    @ApiParam(value = "退款描述")
    @QueryParam(value = "comment")
    private String comment ;

    @ApiParam(value = "审核人")
    @QueryParam(value = "approve_user_name")
    private String approveUserName ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    public BigDecimal getDocumentAmount() {
        return documentAmount;
    }

    public void setDocumentAmount(BigDecimal documentAmount) {
        this.documentAmount = documentAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
