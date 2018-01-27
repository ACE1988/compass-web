package com.sdxd.api.vo.credit;

import com.sdxd.common.web.vo.RestRequest;

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
public class ApprovalMark extends RestRequest {

    @NotNull(message = "准入申请ID未填写")
    private String admittanceId;

    private String tv1;
    private String tv2;
    private String tv3;

    private String remarks;

    public String getAdmittanceId() {
        return admittanceId;
    }

    public void setAdmittanceId(String admittanceId) {
        this.admittanceId = admittanceId;
    }

    public String getTv1() {
        return tv1;
    }

    public void setTv1(String tv1) {
        this.tv1 = tv1;
    }

    public String getTv2() {
        return tv2;
    }

    public void setTv2(String tv2) {
        this.tv2 = tv2;
    }

    public String getTv3() {
        return tv3;
    }

    public void setTv3(String tv3) {
        this.tv3 = tv3;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
