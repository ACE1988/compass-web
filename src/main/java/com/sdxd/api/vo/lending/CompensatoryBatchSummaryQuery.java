package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.QueryParam;
import java.util.Date;

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
 * 2017/9/15     melvin                 Created
 */
public class CompensatoryBatchSummaryQuery extends RestRequest {

    @ApiParam(value = "放款资金提供方ID")
    @QueryParam(value = "capital_provider_id")
    @NotEmpty(message = "资方ID不能为空")
    private String capitalProviderId;

    @ApiParam(value = "日期, 默认是天")
    @QueryParam(value = "day")
    private Date day;

    public String getCapitalProviderId() {
        return capitalProviderId;
    }

    public void setCapitalProviderId(String capitalProviderId) {
        this.capitalProviderId = capitalProviderId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
