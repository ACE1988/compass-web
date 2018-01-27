package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

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
 * 2017/9/27      matteo                 Created
 */
public class ChannelDescribe {
    @ApiModelProperty(value = "文案信息")
    @NotNull(message = "文案信息未填写")
    private String channelDescribe;

    public String getChannelDescribe() {
        return channelDescribe;
    }

    public void setChannelDescribe(String channelDescribe) {
        this.channelDescribe = channelDescribe;
    }
}
