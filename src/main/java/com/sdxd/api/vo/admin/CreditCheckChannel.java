package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

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
 * 2017/8/9    wenzhou.xu              Created
 */
public class CreditCheckChannel {
    @ApiParam(value = "数据源")
    @NotEmpty(message = "请勾选数据源")
    private String resource;
    @ApiParam(value = "接口通道")
    private String channel;
    @ApiParam(value = "查询原因")
    private String reason;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
