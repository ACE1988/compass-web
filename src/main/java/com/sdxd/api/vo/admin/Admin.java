package com.sdxd.api.vo.admin;

import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 * Created by liuam on 2017/4/11.
 */
public class Admin {
    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "注册时间")
    private Date regTime;

    @ApiModelProperty(value = "审批状态：" +
            "<ul><li>0-待审批</li> <li>1-通过</li> <li>2-拒绝</li></ul>", allowableValues = "0,1,2")
    private Integer status;

    @ApiModelProperty(value = "可用状态：" +
            "<ul><li>0-未启用</li> <li>1-启用</li></ul>",
            allowableValues = "0,1")
    private Integer useStatus;

    @ApiModelProperty(value = "白名单")
    private Boolean whiteListUser;

    public Admin() {}

    public Admin(AdminUserResponse response) {
        this.setAccount(response.getId());
        this.setName(response.getName());
        this.setPhone(response.getPhone());
        this.setRegTime(response.getCreateTime());
        this.setStatus(response.getApproveStatus());
        this.setUseStatus(response.getStatus());
        this.setWhiteListUser(response.getWhiteListUser());
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public Boolean getWhiteListUser() {
        return whiteListUser;
    }

    public void setWhiteListUser(Boolean whiteListUser) {
        this.whiteListUser = whiteListUser;
    }
}
