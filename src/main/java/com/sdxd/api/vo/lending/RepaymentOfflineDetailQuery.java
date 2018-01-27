package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.math.BigDecimal;
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
 * 17/06/26     刘节                 Created
 */
public class RepaymentOfflineDetailQuery extends PageParameter {

    @ApiParam(value = "户名")
    @QueryParam(value = "card_name")
    private String cardName;

    //0-待确认，1-待认领,已确认，2-待审核，3-审核通过，4-审核驳回，5-已核销
    @ApiParam(
            value = "到款单状态, 0: 待确认, 1: 已确认,2：待审核,4:审核驳回,3:审核通过,5:已核销",
            allowableValues = "0,1,2,3,4,5")
    @QueryParam(value = "status")
    private Integer status; //状态

    @ApiParam(value = "摘要")
    @QueryParam(value = "abstract")
    private String abstractDesc; //abstract关键字

    @ApiParam(value = "开始时间")
    @QueryParam(value = "form")
    private Date startTime;

    @ApiParam(value = "结束时间")
    @QueryParam(value = "to")
    private Date endTime;

    @ApiParam(value = "备注")
    @QueryParam(value = "remarks")
    private String remarks;//备注

    @ApiParam(value = "开始金额")
    @QueryParam(value = "amount_start")
    private BigDecimal amountStart;

    @ApiParam(value = "结束金额")
    @QueryParam(value = "amount_end")
    private BigDecimal amountEnd;

    @ApiParam(value = "手机号码")
    @QueryParam(value = "phone")
    private String phone;


    @ApiParam(value = "银行")
    @QueryParam(value = "channel")
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAbstractDesc() {
        return abstractDesc;
    }

    public void setAbstractDesc(String abstractDesc) {
        this.abstractDesc = abstractDesc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getAmountStart() {
        return amountStart;
    }

    public void setAmountStart(BigDecimal amountStart) {
        this.amountStart = amountStart;
    }

    public BigDecimal getAmountEnd() {
        return amountEnd;
    }

    public void setAmountEnd(BigDecimal amountEnd) {
        this.amountEnd = amountEnd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
