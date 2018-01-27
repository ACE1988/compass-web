package com.sdxd.api.vo.customer;

import com.sdxd.api.vo.certification.CertificationCompletion;
import com.sdxd.api.vo.credit.CreditLine;
import com.sdxd.common.web.util.FieldIgnore;
import com.sdxd.repayment.dubbo.enums.RepaymentPlanStatus;
import com.sdxd.repayment.dubbo.response.RepaymentPlanInfoExt;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.customer
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/9     melvin                 Created
 */
public class Customer {

    @FieldIgnore
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @FieldIgnore
    @ApiModelProperty(value = "姓名")
    private String name;

    @FieldIgnore
    @ApiModelProperty(value = "性别")
    private String sex;

    @FieldIgnore
    @ApiModelProperty(value = "手机")
    private String cellphone;

    @FieldIgnore
    @ApiModelProperty(value = "额度")
    private CreditLine creditLine;

    @FieldIgnore
    @ApiModelProperty(value = "再借笔数")
    private Integer inLoanCount;

    @FieldIgnore
    @ApiModelProperty(value = "待还金额")
    private BigDecimal repayableAmount;

    @FieldIgnore
    @ApiModelProperty("逾期天数, 小于等于0则未逾期, 大于0则逾期")
    private long daysOverdue;

    @FieldIgnore
    @ApiModelProperty(value = "信息完整度")
    private CertificationCompletion completion;

    @FieldIgnore
    @ApiModelProperty(value = "结案时间")
    private Date finalApproveTime;

    @FieldIgnore
    @ApiModelProperty(value = "申请时间")
    private Date applyTime;

    @FieldIgnore
    @ApiModelProperty(value = "审核状态, 0.未进决策 1.决策中 2.决策异常 3.审核中 4.结案通过 5.结案拒绝")
    private Integer reviewStatus;

    @FieldIgnore
    @ApiModelProperty(value = "审核时长")
    private Long periodOfReview;

    @FieldIgnore
    @ApiModelProperty(value = "注册渠道")
    private String registerChannel;

    @FieldIgnore
    @ApiModelProperty(value = "注册渠道")
    private String applyChannel;

    @FieldIgnore
    @ApiModelProperty(value = "是否有码")
    private Boolean hasAccountCode;

    @FieldIgnore
    @ApiModelProperty(value = "申请编号")
    private String applyAdmittanceId;

    public Boolean getHasAccountCode() {
        return hasAccountCode;
    }

    public void setHasAccountCode(Boolean hasAccountCode) {
        this.hasAccountCode = hasAccountCode;
    }

    public String getRegisterChannel() {
        return registerChannel;
    }

    public void setRegisterChannel(String registerChannel) {
        this.registerChannel = registerChannel;
    }

    public String getApplyChannel() {
        return applyChannel;
    }

    public void setApplyChannel(String applyChannel) {
        this.applyChannel = applyChannel;
    }

    public void calculateOverDue(RepaymentPlanInfoExt plan) {
        if (plan == null) {
            return;
        }
        boolean cleared = plan.getStatus() == RepaymentPlanStatus.ALREADY_REPAYMENT.getValue();
        LocalDate from = plan.getRepaymentTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate to = cleared ?
                plan.getActualRepaymentTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() :
                LocalDate.now(ZoneId.systemDefault());
        long days = ChronoUnit.DAYS.between(from, to);
        setDaysOverdue(days);
    }

    public boolean isCreditLineExist() {
        return creditLine != null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public CreditLine getCreditLine() {
        if (getReviewStatus() != null && getReviewStatus() != 4) {
            return null;
        }
        return creditLine;
    }

    public void setCreditLine(CreditLine creditLine) {
        this.creditLine = creditLine;
    }

    public Integer getInLoanCount() {
        return inLoanCount;
    }

    public void setInLoanCount(Integer inLoanCount) {
        this.inLoanCount = inLoanCount;
    }

    public BigDecimal getRepayableAmount() {
        return repayableAmount;
    }

    public void setRepayableAmount(BigDecimal repayableAmount) {
        this.repayableAmount = repayableAmount;
    }

    public long getDaysOverdue() {
        return daysOverdue;
    }

    public void setDaysOverdue(long daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    public CertificationCompletion getCompletion() {
        return completion;
    }

    public void setCompletion(CertificationCompletion completion) {
        this.completion = completion;
    }

    public Date getFinalApproveTime() {
        return finalApproveTime;
    }

    public void setFinalApproveTime(Date finalApproveTime) {
        this.finalApproveTime = finalApproveTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getReviewStatus() {
//        Integer status = reviewStatus;
        CertificationCompletion completion = getCompletion();
        boolean operators = completion.getRequired().isOperators();
//        boolean certComplete = completion != null && completion.isComplete();
//        if (certComplete && status == null) {
//            return -2;
//        }
//        if (!certComplete && status != null) {
//            return -2;
//        }
//        if (!certComplete) {
//            return -1;
//        }
        if (reviewStatus != null && reviewStatus == 0 && operators) {
            return -1;
        }
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Long getPeriodOfReview() {
        Integer reviewStatus = getReviewStatus();
        if (reviewStatus == null) {
            return null;
        }
        Date end = (reviewStatus == 4 || reviewStatus == 5) ? getFinalApproveTime() : new Date();
        Date start = getApplyTime();
        if (start == null || end == null) {
            return null;
        }
        LocalDate from = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate to = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        periodOfReview = ChronoUnit.DAYS.between(from, to);
        return periodOfReview;
    }

    public void setPeriodOfReview(Long periodOfReview) {
        this.periodOfReview = periodOfReview;
    }


    public String getApplyAdmittanceId() {
        return applyAdmittanceId;
    }

    public void setApplyAdmittanceId(String applyAdmittanceId) {
        this.applyAdmittanceId = applyAdmittanceId;
    }
}
