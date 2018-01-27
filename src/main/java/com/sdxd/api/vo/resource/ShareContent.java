package com.sdxd.api.vo.resource;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.index
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/20     melvin                 Created
 */
public class ShareContent {

    private String id;
    private String title;
    private String content;
    private String imgCode;
    private String imgUrl;
    private String targetUrl;
    private String description;

    public ShareContent(String id, String title, String content, String imgCode, String imgUrl, String targetUrl, String description) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgCode = imgCode;
        this.imgUrl = imgUrl;
        this.targetUrl = targetUrl;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImgCode() {
        return imgCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getDescription() {
        return description;
    }
}
