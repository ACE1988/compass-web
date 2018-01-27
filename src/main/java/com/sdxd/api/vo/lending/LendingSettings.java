package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

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
 * 16/11/30     melvin                 Created
 */
public class LendingSettings extends RestRequest {

    @ApiParam(value = "是否自动放款", required = true)
    @NotNull(message = "是否自动放款未填写")
    @QueryParam(value = "automatic_lending")
    private Boolean automaticLending;

    public Boolean getAutomaticLending() {
        return automaticLending;
    }

    public void setAutomaticLending(Boolean automaticLending) {
        this.automaticLending = automaticLending;
    }
}
