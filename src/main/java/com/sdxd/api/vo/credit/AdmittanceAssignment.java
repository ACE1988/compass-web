package com.sdxd.api.vo.credit;

import com.sdxd.common.web.vo.RestRequest;

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
public class AdmittanceAssignment extends RestRequest {

    @NotNull(message = "信审员ID未填写")
    private String approverId;

    @NotNull(message = "准入申请ID未填写")
    private List<String> admittanceIds;

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public List<String> getAdmittanceIds() {
        return admittanceIds;
    }

    public void setAdmittanceIds(List<String> admittanceIds) {
        this.admittanceIds = admittanceIds;
    }
}
