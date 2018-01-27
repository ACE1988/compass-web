package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

import static com.sdxd.common.utils.ValidatorRegexTmplUtils.REGEX_MOBILE;

/**
 * Created by liuam on 2017/4/12.
 */
public class ResetPassword extends RestRequest {
    @ApiParam(value = "账号")
    @NotEmpty(message = "未填写 account") @Pattern(regexp = "[a-z|A-Z|\\d]+", message = "account 只能由a-z, A-Z或 数字组成")
    private String account;

    @ApiParam(value = "密码")
    @NotEmpty(message = "未填写 password") @Pattern(regexp = "[a-z|A-Z|\\d]+", message = "password 只能由a-z, A-Z或 数字组成")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
