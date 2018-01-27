package com.sdxd.api.vo.cms;

import io.swagger.annotations.ApiModelProperty;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.cms
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/1     melvin                 Created
 */
public class BannerQuery {

    @ApiModelProperty(name = "Banner ID")
    private String id;//d 可以为空
    @ApiModelProperty(name = "名称")
    private String name;
    @ApiModelProperty(allowableValues = "WEB,APP,H5", name = "类型")
    private String type;//类型：WEB，APP，H5,
    @ApiModelProperty(name = "图片code")
    private String imageCode;//图片code
    @ApiModelProperty(name = "页面的id")
    private String pageId;
    @ApiModelProperty(name = "位置")
    private String position;
    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
}
