package com.sdxd.api.vo;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
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
 * 17/3/16     melvin                 Created
 */
public class Roles {

    @ApiParam(name = "role_id", value = "角色ID", defaultValue = "XS_MEMBER", required = true)
    @QueryParam(value = "role_id")
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
