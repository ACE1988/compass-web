package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiModelProperty;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.admin
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/9/27      matteo                 Created
 */
public class ChannelShowType {
    @ApiModelProperty(value = "展示方式")
    private String showType;

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }
}
