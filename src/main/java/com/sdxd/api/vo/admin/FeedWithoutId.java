package com.sdxd.api.vo.admin;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
 * 2017/8/7    wenzhou.xu              Created
 */
public class FeedWithoutId {

    @ApiParam(value = "样式")
    @ApiModelProperty(value = "样式")
    private Integer template;

    @ApiParam(value = "标题")
    @ApiModelProperty(value = "标题")
    @NotBlank(message = "未填写 标题") @Length(min = 1, max = 32, message = "标题 的长度是30字以内，包括标点")
    private String title;

    @ApiParam(value = "标签")
    @ApiModelProperty(value = "标签")
    @NotBlank(message = "未选择 标签")
    private String tag;

    @ApiParam(value = "是否显示，0：不显示，1：显示")
    @ApiModelProperty(value = "是否显示，0：不显示，1：显示")
    @NotNull(message = "未勾选 是否显示")
    private Integer display;

    @ApiParam(value = "图片列表")
    @ApiModelProperty(value = "图片列表")
    @NotNull(message = "未上传图片")
    @Size(min = 3, max = 3, message = "请上传3张图片")
    private List<String> imgList;

    @ApiParam(value = "Feed链接")
    @ApiModelProperty(value = "Feed链接")
    @NotBlank(message = "未填写Feed链接")
    private String feedUrl;

    @ApiParam(value = "来源")
    @ApiModelProperty(value = "来源")
    @NotBlank(message = "未填写来源") @Length(min = 1, max = 8, message = "来源 的长度是8个汉字以内")
    private String from;

    @ApiParam(value = "是否需要评论，0：不需要，1：需要")
    @ApiModelProperty(value = "是否需要评论，0：不需要，1：需要")
    @NotNull(message = "未选择是否需要评论")
    private Integer comment;

    public Integer getTemplate() {
        return template;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }
}
