package com.sdxd.api.vo.credit;

import com.sdxd.api.vo.lending.RepaymentStatus;
import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.credit
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/4/24     liujie                 Created
 */
public class ChannelResourceQuery extends PageParameter {

    @ApiParam(value = "渠道名")
    @QueryParam(value = "channel_name")
    private String channelName;

    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
