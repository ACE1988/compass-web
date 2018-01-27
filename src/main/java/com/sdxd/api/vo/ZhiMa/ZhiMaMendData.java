package com.sdxd.api.vo.ZhiMa;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  ------------------
 * 2018/1/12    刘节                 Created
 */
public class ZhiMaMendData {

    @ApiModelProperty(value = "订单列表 ex:APY1017111211414400000,APY1117102010051200000", required = true)
    @NotEmpty(message = "订单字符列表不能为空")
    private String applyIds; //订单

    public String getApplyIds() {
        return applyIds;
    }

    public void setApplyIds(String applyIds) {
        this.applyIds = applyIds;
    }
}
