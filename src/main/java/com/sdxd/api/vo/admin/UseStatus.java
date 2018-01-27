package com.sdxd.api.vo.admin;

import com.sdxd.admin.dubbo.api.response.OffResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by liuam on 2017/4/13.
 */
public class UseStatus {
    @ApiModelProperty(value = "启用状态")
    private Boolean off;

    public UseStatus() {}

    public UseStatus(OffResponse response) {
        this.setOff(response.isOff());
    }

    public Boolean getOff() {
        return off;
    }

    public void setOff(Boolean off) {
        this.off = off;
    }
}
