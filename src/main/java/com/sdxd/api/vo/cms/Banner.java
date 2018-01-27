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
public class Banner {

    @ApiModelProperty(required = true, name = "名称")
    private String name;
    @ApiModelProperty(required = true, allowableValues = "WEB,APP,H5", name = "类型")
    private String type;//类型：WEB，APP，H5,
    @ApiModelProperty(required = true, name = "图片code")
    private String imageCode;//图片code
    @ApiModelProperty(required = true, name = "图片地址")
    private String imageUrl;//图片地址
    @ApiModelProperty(required = true, name = "图片连接地址")
    private String imageLink;//图片连接地址
    @ApiModelProperty(required = true, name = "图片的顺序")
    private Integer imageOrder;//图片的顺序，
    @ApiModelProperty(required = true, name = "页面的id")
    private String pageId;
    @ApiModelProperty(required = true, name = "位置")
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(Integer imageOrder) {
        this.imageOrder = imageOrder;
    }
}
