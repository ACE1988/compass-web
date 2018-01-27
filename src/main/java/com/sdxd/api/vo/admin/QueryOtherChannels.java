package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author liujie
 * @Date 2017/5/26
 * 盛大小贷
 */
public class QueryOtherChannels extends PageParameter {
    @ApiModelProperty(value = "平台类型")
    private String type ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
