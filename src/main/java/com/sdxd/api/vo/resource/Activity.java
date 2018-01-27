package com.sdxd.api.vo.resource;

import com.sdxd.activities.api.dto.activity.ActivityInfo;
import com.sdxd.activities.api.dto.activity.ActivitySettingItem;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.resource
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/7/31     melvin                 Created
 */
public class Activity {

    private String id;

    @ApiModelProperty("活动标记key")
    private String activityKey;

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动描述")
    private String activityDescription;

    @ApiModelProperty("图片地址")
    private String imgUrl;

    @ApiModelProperty("详情链接")
    private String detailUrl;

    @ApiModelProperty("活动状态 0： 有效   1：无效")
    private Integer status;

    @ApiModelProperty("活动开始时间")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    private Date endTime;

    @ApiModelProperty("展示开始时间")
    private Date showStartTime;

    @ApiModelProperty("展示结束时间")
    private Date showEndTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    private List<ActivitySetting> settings;

    public Activity(ActivityInfo info, List<ActivitySettingItem> items) {
        if (info != null) {
            this.id = info.getId();
            this.activityKey = info.getActivityKey();
            this.activityName = info.getActivityName();
            this.activityDescription = info.getDescription();
            this.imgUrl = info.getImgUrl();
            this.detailUrl = info.getDetailUrl();
            this.status = info.getStatus();
            this.startTime = info.getStartTime();
            this.endTime = info.getEndTime();
            this.showStartTime = info.getShowStartTime();
            this.showEndTime = info.getShowEndTime();
            this.createTime = info.getCreateTime();
            this.updateTime = info.getUpdateTime();
            if (items != null) {
                this.settings = items.stream().map(ActivitySetting::new).collect(Collectors.toList());
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getActivityKey() {
        return activityKey;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public List<ActivitySetting> getSettings() {
        return settings;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public Date getShowStartTime() {
        return showStartTime;
    }

    public Date getShowEndTime() {
        return showEndTime;
    }
}
