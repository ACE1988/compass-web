package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2017/4/13.
 */
public class AdminRoleUpdate {

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "角色操作状态: -1 移除 1 新增 0 正常")
    private Integer operation;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }
}
