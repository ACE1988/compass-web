package com.sdxd.api.vo.lending;

import com.sdxd.approve.dubbo.api.enums.PageOrderField;
import com.sdxd.common.web.vo.RestRequest;
import com.sdxd.loan.enums.SettingVariable;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

/**
 * @author liujie
 * @Date 2017/5/16
 * 盛大小贷
 */
public class LoanParameterSetting extends RestRequest {
    @ApiParam(
            value = "AUTO_LOAN_SETTING: 是否自动放款, USER_LOAN_SINGLE_AMOUNT: 用户单笔金额限制,TOTAL_LOAN_TIMES:当日总笔数限制," +
                    "USER_LOAN_TIMES:用户每天放款次数限制,TOTAL_LOAN_DAY_AMOUNT:单日放款总金额限制,USER_FAIL_DAY_TIME:用户每日每笔放款失败次数限制",
            allowableValues = "AUTO_LOAN_SETTING,USER_LOAN_SINGLE_AMOUNT,TOTAL_LOAN_TIMES,USER_LOAN_TIMES,TOTAL_LOAN_DAY_AMOUNT,USER_FAIL_DAY_TIME",required = true)
    @FormParam(value = "key")
    @NotNull(message = "放款KEY值未填写")
    private SettingVariable key;

    @ApiParam(value = "参数VALUE",required = true)
    @FormParam(value = "value")
    @NotNull(message = "参数VALUE值未填写")
    private String value;

    public SettingVariable getKey() {
        return key;
    }

    public void setKey(SettingVariable key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
