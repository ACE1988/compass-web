package com.sdxd.api.vo.lending;

import com.sdxd.common.web.util.FieldIgnore;
import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

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
 * 17/3/17     melvin                 Created
 */
public class PaymentProxyQuery extends PageParameter {

    @ApiParam(value = "客户手机号码")
    @QueryParam(value = "cellphone")
    private String cellphone;

    @ApiParam(value = "客户姓名")
    @QueryParam(value = "user_name")
    private String userName; //用户姓名

    @FieldIgnore
    private Long userId;

    @ApiParam(value = "借款订单号")
    @QueryParam(value = "apply_id")
    private String applyId;  //借款订单号

    @ApiParam(value = "盛付通:状态, 1: 新建, 11: 银行卡代扣中, 12: 银行卡代扣成功, 13: 银行卡代扣失败, 21: 还款中, 22: 还款成功, 23: 还款失败;宝付:状态,0:新创建,1:处理中,2:代付成功,3:代付失败",
            allowableValues = "盛付通:1,11,12,13,21,22,23;宝付:0,1,2,3")
    @QueryParam(value = "status")
    private Byte status;

    @ApiParam(value = "银行编码，如CCB")
    @QueryParam(value = "bank_code")
    private String bankCode; //银行编码，如CCB

    @ApiParam(value = "指令流水号")
    @QueryParam(value = "instruction_id")
    private String id;       //指令流水号

    @ApiParam(value = "回调时间开始")
    @QueryParam(value = "call_back_from")
    private Date startTime;  //回调时间

    @ApiParam(value = "回调时间结束")
    @QueryParam(value = "call_back_to")
    private Date endTime;    //回调时间

    @ApiParam(value = "支付渠道")
    @QueryParam(value = "channel")
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
