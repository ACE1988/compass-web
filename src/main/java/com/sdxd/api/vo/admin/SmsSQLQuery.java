package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.ws.rs.FormParam;

/**
 * author : zhangxu
 */
public class SmsSQLQuery {

    @ApiParam(value = "SqlQuery", required = true)
    @FormParam(value = "sqlQuery")
    private String sqlQuery;

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }
}
