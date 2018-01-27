package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import com.sdxd.loan.enums.LoanStatus;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.lending
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/12/6     melvin                 Created
 */
public class LendingQuery extends RestRequest {

    @ApiParam(value = "产品类型, 小额单期: small_one_period", allowableValues = "small_one_period")
    @QueryParam(value = "product_category")
    private String productCategory;

    @ApiParam(value = "合同编号")
    @QueryParam(value = "contract_no")
    private String contractNo;

    @ApiParam(value = "借款客户")
    @QueryParam(value = "lender")
    private String lender;

    @ApiParam(value = "银行卡号")
    @QueryParam(value = "bank_card_no")
    private String bankCardNo;

    @ApiParam(value = "申请金额", allowableValues = "500,1000,2000")
    @QueryParam(value = "amount")
    private String amount;

    @ApiParam(value = "申请期限", allowableValues = "7,14")
    @QueryParam(value = "period")
    private String period;

    @ApiParam(value = "到账时间(起始), 默认为上一个月的今天")
    @QueryParam(value = "apply_time_start")
    private Date startArrivalTime;

    @ApiParam(value = "到账时间(结束), 默认为今天")
    @QueryParam(value = "apply_time_end")
    private Date endArrivalTime;

    @ApiParam(value = "资方ID")
    @QueryParam(value = "capital_provider_id")
    private String capitalProviderId;

    @ApiParam(value = "放款通道编号")
    @QueryParam(value = "lending_channel_code")
    private String lendingChannelCode;

    @ApiParam(value = "拒绝原因")
    @QueryParam(value = "reject_reason")
    private String rejectReason;

    @ApiParam(
            value = "放款状态, 0-待放款(NEW) 12-放款中(PAYING) 21-放款失败(FAIL) 22-放款拒绝(REJECT) 23-放款异常(EXCEPTION) 29-放款成功(SUCCESS) 32-取消放款成功(CANCEL_SUCCESS)",
            allowableValues = "NEW,PAYING,FAIL,REJECT,EXCEPTION,SUCCESS,CANCEL_SUCCESS")
    @QueryParam(value = "lending_status")
    private LoanStatus lendingStatus;

    @ApiParam(value = "页编号", required = true)
    @NotNull(message = "页编号未填写")
    @QueryParam(value = "page_no")
    private Integer currentPage;

    @ApiParam(value = "每页条数", required = true)
    @NotNull(message = "每页条数未填写")
    @QueryParam(value = "page_size")
    private Integer pageSize;

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getStartArrivalTime() {
        return startArrivalTime;
    }

    public void setStartArrivalTime(Date startArrivalTime) {
        this.startArrivalTime = startArrivalTime;
    }

    public Date getEndArrivalTime() {
        return endArrivalTime;
    }

    public void setEndArrivalTime(Date endArrivalTime) {
        this.endArrivalTime = endArrivalTime;
    }

    public String getCapitalProviderId() {
        return capitalProviderId;
    }

    public void setCapitalProviderId(String capitalProviderId) {
        this.capitalProviderId = capitalProviderId;
    }

    public String getLendingChannelCode() {
        return lendingChannelCode;
    }

    public void setLendingChannelCode(String lendingChannelCode) {
        this.lendingChannelCode = lendingChannelCode;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public LoanStatus getLendingStatus() {
        return lendingStatus;
    }

    public void setLendingStatus(LoanStatus lendingStatus) {
        this.lendingStatus = lendingStatus;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
