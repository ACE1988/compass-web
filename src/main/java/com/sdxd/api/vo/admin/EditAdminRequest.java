package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

import static com.sdxd.common.utils.ValidatorRegexTmplUtils.REGEX_MOBILE;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.certificate
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/9     melvin                 Created
 */
public class EditAdminRequest extends RestRequest {
    @ApiParam(value = "账号")
    @NotEmpty(message = "未填写 account")
    @Pattern(regexp = "[a-z|A-Z|\\d]+", message = "account 只能由a-z, A-Z或 数字组成")
    private String account;
    @ApiParam(value = "姓名")
    @NotEmpty(message = "未填写 name")
    @Pattern(regexp = "[^x00-xff]+", message = "name 必须是中文")
    @Length(min = 2, max = 20, message = "name 的长度是6-20")
    private String name;
    @ApiParam(value = "电话")
    @NotEmpty(message = "未填写 phone")
    @Pattern(regexp = REGEX_MOBILE, message = "phone 格式是错误的")
    private String phone;
    @ApiParam(value = "白名单")
    private Boolean whiteListUser;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getWhiteListUser() {
        return whiteListUser;
    }

    public void setWhiteListUser(Boolean whiteListUser) {
        this.whiteListUser = whiteListUser;
    }
}
