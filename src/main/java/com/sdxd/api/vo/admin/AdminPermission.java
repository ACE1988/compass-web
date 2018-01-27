package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiModelProperty;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.admin
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/4/13     melvin                 Created
 */
public class AdminPermission {

    @ApiModelProperty(value = "权限ID")
    private String id;

    @ApiModelProperty(value = "权限名")
    private String name;

    public AdminPermission(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
