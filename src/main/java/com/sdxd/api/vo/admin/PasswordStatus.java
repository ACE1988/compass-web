package com.sdxd.api.vo.admin;

import com.sdxd.admin.dubbo.api.response.ResetPassWordResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by liuam on 2017/4/13.
 */
public class PasswordStatus {
    @ApiModelProperty(value = "密码重置返回值")
    private Boolean success;

    public PasswordStatus() {}

    public PasswordStatus(ResetPassWordResponse response) {
        this.setSuccess(response.getIsSuccess());
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
