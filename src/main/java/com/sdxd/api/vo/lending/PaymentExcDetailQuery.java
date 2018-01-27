package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/30     liujie                 Created
 */
public class PaymentExcDetailQuery extends PageParameter {

    @ApiParam(value = "开始时间", required = true)
    @QueryParam(value = "from")
    private Date startTime;

    @ApiParam(value = "结束时间", required = true)
    @QueryParam(value = "to")
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
