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
public class ApprovalStash {

    @NotNull(message = "准入申请ID未填写")
    private String admittanceId;

    public String getAdmittanceId() {
        return admittanceId;
    }

    public void setAdmittanceId(String admittanceId) {
        this.admittanceId = admittanceId;
    }
}
