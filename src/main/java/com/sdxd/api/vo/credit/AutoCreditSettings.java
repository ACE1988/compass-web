package com.sdxd.api.vo.credit;

import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.credit
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/9/26     melvin                 Created
 */
public class AutoCreditSettings {

    @ApiParam(name = "codes", value = "代码", required = true)
    @NotBlank(message = "代码未填写")
    @FormParam("codes")
    private String codes;
    @ApiParam(name = "auto", value = "是否自动", required = true)
    @NotNull(message = "是否自动未填写")
    @FormParam("auto")
    private Boolean auto;
    @ApiParam(name = "sms_count", value = "短信数量", required = true)
    @NotNull(message = "短信数量未填写")
    @FormParam(value = "sms_count")
    private Integer smsCount;
    @ApiParam(name = "phone", value = "发送的手机号e.g.（1381722876，13356229988）", required = true)
    @NotBlank(message = "电话号码未填写")
    @FormParam(value = "phone")
    private String phone;

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public Integer getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(Integer smsCount) {
        this.smsCount = smsCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
