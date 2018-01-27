package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

/**
 * @author liujie
 * @Date 2017/6/26
 * 盛大小贷
 */
public class PayOfflineLogsQuery extends RestRequest{

    @ApiParam(value = "到账单id")
    @QueryParam(value = "id")
    @NotNull
    private String id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
