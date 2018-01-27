package com.sdxd.api.vo.credit;

import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.QueryParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.credit
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/6     melvin                 Created
 */
public class Admittance {

    @ApiParam(value = "准入申请编号", required = true)
    @NotEmpty(message = "准入申请编号未填写")
    @QueryParam(value = "admittance_id")
    private String admittanceId;

    public String getAdmittanceId() {
        return admittanceId;
    }

    public void setAdmittanceId(String admittanceId) {
        this.admittanceId = admittanceId;
    }
}
