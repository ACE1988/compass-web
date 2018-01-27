package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * @author liujie
 * @Date 2017/4/27
 * 盛大小贷
 */
public class RepaymentDetailQuery extends PageParameter {

    @ApiParam(value = "客户姓名")
    @QueryParam(value = "user_name")
    private String userName ;

    @ApiParam(value = "AUTO_DEDUCT:自动代扣,MANUAL_DEDUCT:手工主动代扣,OVERDUE_AUTO_DEDUCT:" +
            "逾期自动代扣,URGE_MANUAL_DEDUCT:催收手工强扣,NORMAL_ONLINE_APP:主动线上APP还款," +
            "NORMAL_ONLINE_H5:主动线上H5还款,NORMAL_OFFLINE:主动线下还款",
    allowableValues = "AUTO_DEDUCT,MANUAL_DEDUCT,OVERDUE_AUTO_DEDUCT,URGE_MANUAL_DEDUCT," +
            "NORMAL_ONLINE_APP,NORMAL_ONLINE_H5,NORMAL_OFFLINE")
    @QueryParam(value = "repayment_method")
    private String repaymentMethod ;

    @ApiParam(
            value = "2:待核销, 3: 部分核销, 4: 核销完成",
            allowableValues = "2,3,4")
    @QueryParam(value = "status")
    private Byte status ;

    @ApiParam(value = "SHENGPAY:盛付通,p2pshengPay:盛付通托管账户,baofoo:宝付",
    allowableValues = "SHENGPAY,p2pshengPay,baofoo")
    @QueryParam(value = "repayment_channel")
    private String repaymentChannel;

    @ApiParam(value = "还款时间")
    @QueryParam(value = "start_repayment_time")
    private Date startRepaymentTime;

    @ApiParam(value = "还款时间")
    @QueryParam(value = "end_repayment_time")
    private Date endRepaymentTime;

    @ApiParam(value = "借款订单号")
    @QueryParam(value = "apply_id")
    private String applyId ;

    @ApiParam(value = "渠道流水号")
    @QueryParam(value = "id")
    private String id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRepaymentChannel() {
        return repaymentChannel;
    }

    public void setRepaymentChannel(String repaymentChannel) {
        this.repaymentChannel = repaymentChannel;
    }

    public Date getStartRepaymentTime() {
        return startRepaymentTime;
    }

    public void setStartRepaymentTime(Date startRepaymentTime) {
        this.startRepaymentTime = startRepaymentTime;
    }

    public Date getEndRepaymentTime() {
        return endRepaymentTime;
    }

    public void setEndRepaymentTime(Date endRepaymentTime) {
        this.endRepaymentTime = endRepaymentTime;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
