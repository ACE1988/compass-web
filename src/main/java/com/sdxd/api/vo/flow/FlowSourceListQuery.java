package com.sdxd.api.vo.flow;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.flow
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/10/26      matteo                 Created
 */
public class FlowSourceListQuery extends RestRequest {
    //    渠道组的id    前端登录可以获得这个数据 group
    @ApiParam(value = "渠道组id")
    @QueryParam(value = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
