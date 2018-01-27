package com.sdxd.api.vo.credit;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

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
public class CreditApproval {

    @NotNull(message = "准入申请ID未填写")
    private List<String> admittanceIds;

    @NotNull(message = "审核结果未填写")
    private ApprovalStatus result;

    @ApiModelProperty(allowableValues =
            "DF01,DF02,DF03,DF04,DC01,DC02,DC03,DC04,DP01,DP02,DP03,DP04,DM01,DO01,DO02,DO03,DO04,DO05,DO06,DO07")
    private String rejectCode;

    private String rejectReason;

    private Boolean batch;

    private String comment;

    public boolean isMultipleSelection() {
        return admittanceIds != null && admittanceIds.size() > 1;
    }

    public List<String> getAdmittanceIds() {
        return admittanceIds;
    }

    public void setAdmittanceIds(List<String> admittanceIds) {
        this.admittanceIds = admittanceIds;
    }

    public ApprovalStatus getResult() {
        return result;
    }

    public void setResult(ApprovalStatus result) {
        this.result = result;
    }

    public String getRejectCode() {
        return rejectCode;
    }

    public void setRejectCode(String rejectCode) {
        this.rejectCode = rejectCode;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Boolean getBatch() {
        return batch;
    }

    public void setBatch(Boolean batch) {
        this.batch = batch;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
