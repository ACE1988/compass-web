package com.sdxd.api.vo.credit;

import javax.validation.constraints.NotNull;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/24     melvin                 Created
 */
public class CreditApprovalReturn {

    @NotNull(message = "准入申请ID未填写")
    private String admittanceId;

    @NotNull(message = "拒绝原因")
    private String rejectReason;

    private String comment;

    public String getAdmittanceId() {
        return admittanceId;
    }

    public void setAdmittanceId(String admittanceId) {
        this.admittanceId = admittanceId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
