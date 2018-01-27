package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

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
public class RolesChanging {

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "变更状态, 1: 新增, 0: 不变, -1: 删除")
    private Integer changingStatus;

    @ApiModelProperty(value = "权限列表")
    private List<String> permissions;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChangingStatus() {
        return changingStatus;
    }

    public void setChangingStatus(Integer changingStatus) {
        this.changingStatus = changingStatus;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
