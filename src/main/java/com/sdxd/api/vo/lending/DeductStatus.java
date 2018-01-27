package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import com.sdxd.pay.enums.PayChannel;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.QueryParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.lending
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/22     melvin                 Created
 */
public class DeductStatus extends RestRequest {

    @ApiParam(value = "支付渠道, SHENGPAY: 盛付通代付代扣通道, BAOFOO: 宝付代付代扣, P2P_SHENGPAY: 盛付通托管账号",
            defaultValue = "SHENGPAY", required = true)
    @NotEmpty(message = "支付渠道未填写")
    @QueryParam(value = "pay_channel")
    private PayChannel channel;

    @ApiParam(value = "修改为成功还是失败", required = true)
    @NotEmpty(message = "状态未填写")
    @QueryParam(value = "success")
    private Boolean success;

    @ApiParam(value = "描述")
    @QueryParam(value = "description")
    private String description;

    public PayChannel getChannel() {
        return channel;
    }

    public void setChannel(PayChannel channel) {
        this.channel = channel;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
