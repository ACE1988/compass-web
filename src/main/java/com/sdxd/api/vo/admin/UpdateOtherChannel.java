package com.sdxd.api.vo.admin;

import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * @author liujie
 * @Date 2017/5/26
 * 盛大小贷
 */
public class UpdateOtherChannel extends RestRequest {

    @ApiModelProperty(required = true, value = "logo地址")
    private String channelLogo;

    @ApiModelProperty(required = true, value = "平台名称")
    private String channelName;

    @ApiModelProperty(required = true, value = "平台slogn")
    private String channelSlogn;

    @ApiModelProperty(required = true, value = "平台Url地址")
    private String channelUrl;

    @ApiModelProperty(required = true, value = "平台位置")
    private Integer channelIndex;

    @ApiModelProperty(required = true, value = "类型 app:设备, h5: 网页")
    private String channelType;//平台类型

    @ApiModelProperty(
            value = "是否显示, 0: 列表, 1: 网格",
            allowableValues = "0,1")
    private List<String> channelShowWay;

    @ApiModelProperty(required = true, value = "列表图片来源logo地址")
    private String channelImage;

    @ApiModelProperty(
            value = "是否显示, 1: 显示, 0: 不显示",
            allowableValues = "1,0")
    private Integer channelShow;

    public List<String> getChannelShowWay() {
        return channelShowWay;
    }

    public void setChannelShowWay(List<String> channelShowWay) {
        this.channelShowWay = channelShowWay;
    }

    public String getChannelImage() {
        return channelImage;
    }

    public void setChannelImage(String channelImage) {
        this.channelImage = channelImage;
    }

    public String getChannelLogo() {
        return channelLogo;
    }

    public void setChannelLogo(String channelLogo) {
        this.channelLogo = channelLogo;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelSlogn() {
        return channelSlogn;
    }

    public void setChannelSlogn(String channelSlogn) {
        this.channelSlogn = channelSlogn;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public Integer getChannelIndex() {
        return channelIndex;
    }

    public void setChannelIndex(Integer channelIndex) {
        this.channelIndex = channelIndex;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getChannelShow() {
        return channelShow;
    }

    public void setChannelShow(Integer channelShow) {
        this.channelShow = channelShow;
    }
}
