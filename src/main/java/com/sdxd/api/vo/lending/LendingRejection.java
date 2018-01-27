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
 * 16/11/30     melvin                 Created
 */
public class LendingRejection {

    @ApiModelProperty(value = "放款记录ID", required = true)
    @NotNull(message = "放款记录ID未填写")
    private String lendingId;

    @ApiModelProperty(value = "拒绝原因", required = true)
    @NotNull(message = "拒绝原因未填写")
    private String reason;

    @ApiModelProperty(value = "备注")
    private String comment;

    public String getLendingId() {
        return lendingId;
    }

    public void setLendingId(String lendingId) {
        this.lendingId = lendingId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
