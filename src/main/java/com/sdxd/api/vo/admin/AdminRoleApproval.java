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
 * 17/4/17     melvin                 Created
 */
public class AdminRoleApproval {

    @ApiModelProperty(value = "操作, 1: 通过, 2: 拒绝", allowableValues = "1,2")
    private Integer operation;

    @ApiModelProperty(value = "描述")
    private String comment;

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
