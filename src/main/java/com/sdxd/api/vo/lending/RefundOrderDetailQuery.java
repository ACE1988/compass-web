package com.sdxd.api.vo.lending;


import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 * @author liujie
 * @Date 2017/5/9
 * 盛大小贷 退款单还款详情查询
 */
public class RefundOrderDetailQuery  extends RestRequest {

    @ApiParam(value = "订单编号")
    @QueryParam(value = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
