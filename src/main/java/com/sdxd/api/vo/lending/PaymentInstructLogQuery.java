package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 * @author liujie
 * @Date 2017/4/24
 * 盛大小贷
 */
public class PaymentInstructLogQuery extends RestRequest {

    @ApiParam(value = "代付指令ID")
    @QueryParam(value = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
