package com.sdxd.api.vo.lending;

import com.sdxd.loan.enums.LoanStatus;
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
 * 16/11/30     melvin                 Created
 */
public class ExceptionLending {

    @ApiModelProperty(value = "放款记录ID", required = true)
    @NotNull(message = "放款记录ID未填写")
    private String lendingId;

    @ApiModelProperty(value = "状态, FAIL: 放款失败, SUCCESS: 放款成功", required = true, allowableValues = "SUCCESS,FAIL")
    @NotNull(message = "状态未填写")
    private LoanStatus status;

    @ApiModelProperty(value = "备注")
    private String comment;

    public String getLendingId() {
        return lendingId;
    }

    public void setLendingId(String lendingId) {
        this.lendingId = lendingId;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
