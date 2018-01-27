package com.sdxd.api.vo.lending;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

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
 * 2017/11/20      matteo                 Created
 */
public class LoanCancelExecute {
    @NotNull(message = "放款记录ID未填写")
    @ApiModelProperty(value = "放款记录ID")
    private String loanId;

    @NotNull(message = "操作人ID未填写")
    @ApiModelProperty(value = "操作人ID")
    private String operatorId;

    @NotNull(message = "操作者姓名未填写")
    @ApiModelProperty(value = "操作者姓名")
    private String operatorName;

    @ApiModelProperty(value = "拒绝原因")
    private String rejectReason;


    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

}
