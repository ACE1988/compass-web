package com.sdxd.api.vo.lending;

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
 * 17/3/15     melvin                 Created
 */
public class RepaymentSdxdQuery extends PageParameter {

    @ApiParam(value = "客户姓名")
    @QueryParam(value = "user_name")
    private String userName;

    @ApiParam(value = "订单编号")
    @QueryParam(value = "apply_id")
    private String applyId;

    @ApiParam(value = "手机号码")
    @QueryParam(value = "phone")
    private String phone;

    @ApiParam(
            value = "还款状态, PENDING_REPAYMENT: 未还清, ALREADY_REPAYMENT: 已还清",
            allowableValues = "PENDING_REPAYMENT,ALREADY_REPAYMENT")
    @QueryParam(value = "status")
    private RepaymentStatus status;

    @ApiParam(value = "逾期天数（最小）")
    @QueryParam(value = "min_overdue_days")
    private Integer minOverdueDays;

    @ApiParam(value = "逾期天数（最大）")
    @QueryParam(value = "max_overdue_days")
    private Integer maxOverdueDays;

    @ApiParam(value = "借款到期日（开始）")
    @QueryParam(value = "repayment_time_from")
    private Date startRepaymentTime;

    @ApiParam(value = "借款到期日（结束）")
    @QueryParam(value = "repayment_time_to")
    private Date endRepaymentTime;

    @ApiParam(value = "借款起始日期（开始）")
    @QueryParam(value = "loan_time_from")
    private Date startLoanTime;

    @ApiParam(value = "借款起始日期（结束）")
    @QueryParam(value = "loan_time_to")
    private Date endLoanTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RepaymentStatus getStatus() {
        return status;
    }

    public void setStatus(RepaymentStatus status) {
        this.status = status;
    }

    public Integer getMinOverdueDays() {
        return minOverdueDays;
    }

    public void setMinOverdueDays(Integer minOverdueDays) {
        this.minOverdueDays = minOverdueDays;
    }

    public Integer getMaxOverdueDays() {
        return maxOverdueDays;
    }

    public void setMaxOverdueDays(Integer maxOverdueDays) {
        this.maxOverdueDays = maxOverdueDays;
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

    public Date getStartLoanTime() {
        return startLoanTime;
    }

    public void setStartLoanTime(Date startLoanTime) {
        this.startLoanTime = startLoanTime;
    }

    public Date getEndLoanTime() {
        return endLoanTime;
    }

    public void setEndLoanTime(Date endLoanTime) {
        this.endLoanTime = endLoanTime;
    }
}
