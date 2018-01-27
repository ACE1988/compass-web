package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.QueryParam;
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
 * 2017/8/9    wenzhou.xu              Created
 */
public class CreditCheck extends RestRequest {
    @ApiParam(value = "批次号")
    @QueryParam(value = "batch_id")
    @NotEmpty(message = "批次号不能为空")
    private String batchId;

    @ApiParam(value = "征信渠道")
    @QueryParam(value = "channel_list")
    @NotEmpty(message = "请至少勾选一个征信源")
    private List<CreditCheckChannel> channelList;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public List<CreditCheckChannel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<CreditCheckChannel> channelList) {
        this.channelList = channelList;
    }
}
