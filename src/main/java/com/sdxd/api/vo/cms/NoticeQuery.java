package com.sdxd.api.vo.cms;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

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
public class NoticeQuery {

    @ApiModelProperty(name = "Notice ID")
    private String id; //更新  删除必传
    @ApiModelProperty(name = "标题")
    private String title;
    @ApiModelProperty(name = "链接")
    private String link;
    @ApiModelProperty(name = "下线时间")
    private Date offlineTime;

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
