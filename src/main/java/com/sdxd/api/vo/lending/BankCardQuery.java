package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import javax.validation.constraints.NotNull;

/**
 * @author liujie
 * @Date 2017/5/11
 * 盛大小贷
 */
public class BankCardQuery extends RestRequest {

    @ApiParam(value = "用户id")
    @QueryParam(value = "user_id")
    @NotNull
    private Long userId ;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
