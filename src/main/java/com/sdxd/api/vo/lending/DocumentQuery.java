package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 * @author liujie
 * @Date 2017/5/14
 * 盛大小贷
 */
public class DocumentQuery extends RestRequest {

    @ApiParam(value = "申请编号")
    @QueryParam(value = "apply_id")
    private String applyId;

    @ApiParam(value = "用户ID")
    @QueryParam(value = "user_id")
    private Long userId;

    @ApiParam(value = "还款计划Id")
    @QueryParam(value = "plan_id")
    private String planId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
