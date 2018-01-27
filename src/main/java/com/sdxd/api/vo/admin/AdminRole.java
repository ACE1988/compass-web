package com.sdxd.api.vo.admin;

import com.google.common.collect.Lists;
import com.sdxd.admin.dubbo.api.response.RolePermissionVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */
public class AdminRole {

    @ApiModelProperty(value = "角色ID")
    private String id;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色操作状态: -1 移除 1 新增 0 正常")
    private String operation;

    @ApiModelProperty(value = "系统名称")
    private String system;

    @ApiModelProperty(value = "权限列表")
    private List<AdminPermission> permissions;

    public AdminRole(RolePermissionVo rolePermission) {
        if (rolePermission != null) {
            setId(rolePermission.getRoleId());
            setName(rolePermission.getRoleName());
            setOperation(rolePermission.getRoleStatus());
            setSystem(rolePermission.getSystemName());
            mergePermission(rolePermission);
        }
    }

    public void mergePermission(RolePermissionVo rolePermission) {
        if (getPermissions() == null) {
            setPermissions(Lists.newArrayList());
        }
        if (rolePermission != null) {
            getPermissions().addAll(Collections.singletonList(
                    new AdminPermission(
                            rolePermission.getPermissionId(),
                            rolePermission.getPermissionName())));
        }
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public List<AdminPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AdminPermission> permissions) {
        this.permissions = permissions;
    }
}
