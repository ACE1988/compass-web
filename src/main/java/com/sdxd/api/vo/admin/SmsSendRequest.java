package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.ws.rs.FormParam;

@Data
public class SmsSendRequest {

    @ApiParam(value = "查询SQL语句", required = true)
    @FormParam("sqlQuery")
    private String sqlQuery;

    @ApiParam(value = "短信签名", required = true)
    @FormParam("sign")
    private String sign;

    @ApiParam(value = "选择通道", required = true)
    @FormParam("channel")
    private String channel;

    @ApiParam(value = "短信内容", required = true)
    @FormParam("content")
    private String content;


}
