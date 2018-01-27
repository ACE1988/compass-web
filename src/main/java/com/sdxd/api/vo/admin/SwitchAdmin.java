package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import javax.ws.rs.QueryParam;

/**
 * Created by liuam on 2017/4/12.
 */
public class SwitchAdmin extends RestRequest {
    @ApiParam(value = "账号")
    @QueryParam(value = "account")
    @Pattern(regexp = "[a-z|A-Z|\\d]+", message = "account 只能由a-z, A-Z或 数字组成") 
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
