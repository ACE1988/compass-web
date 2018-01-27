package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 * @author liujie
 * @Date 2017/5/14
 * 盛大小贷
 */
public class RepaymentPlanQuery extends RestRequest {

    @ApiParam(value = "申请单ID")
    @QueryParam(value = "apply_id")
    private String applyId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
