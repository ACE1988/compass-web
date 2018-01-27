package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.lending
 * 系统名           ：提交订单
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/06/26     刘节                 Created
 */
public class RepaymentOfflineApproveCommit extends RestRequest {

    @ApiModelProperty(value = "对账单id", required = true)
    @NotNull(message = "对账id未填写")
    private List<String> ids ;

    @ApiParam(
            value = "1: 确定, 0: 删除",
            allowableValues = "1,0",required = true)
    @FormParam(value = "type")
    @NotNull(message = "借款参数key值未填写")
    private Integer type;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
