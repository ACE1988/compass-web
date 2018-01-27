package com.sdxd.api.vo.lending;

import com.sdxd.apply.enums.SettingVariable;
import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

/**
 * @author liujie
 * @Date 2017/5/16
 * 盛大小贷
 */
public class PayParameterSetting extends RestRequest {

    @ApiParam(
            value = "APPLY_SWITCH: 是否支持申请操作, APPLY_START_TIME: 每天申请贷款开始时间,APPLY_END_TIME:每天申请借款的结束时间," +
                    "ROLLOVER_DAY:可提前展期天数,ROLLOVER_ENDTIME:展期到期的时间点",
            allowableValues = "APPLY_SWITCH,APPLY_START_TIME,APPLY_END_TIME,ROLLOVER_DAY,ROLLOVER_ENDTIME",required = true)
    @FormParam(value = "key")
    @NotNull(message = "借款参数key值未填写")
    private SettingVariable key;

    @ApiParam(value = "参数VALUE",required = true)
    @FormParam(value = "value")
    @NotNull(message = "借款参数VALUE值未填写")
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
