package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liujie
 * @Date 2017/6/5
 * 盛大小贷
 */
public class OnOffOtherChannel extends RestRequest {

    @ApiModelProperty(
            value ="开关渠道 1：打开 ; 0 ：关闭",
            allowableValues = "1,0")
    private Integer onOff ;

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }
}
