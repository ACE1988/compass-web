package com.sdxd.api.vo.resource;

import com.sdxd.activities.api.dto.activity.ActivitySettingItem;
import io.swagger.annotations.ApiModelProperty;

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
public class ActivitySetting {

    @ApiModelProperty("活动标记key")
    private String activityKey;

    @ApiModelProperty("Setting名")
    private String item;

    @ApiModelProperty("Setting值")
    private String itemValue;

    @ApiModelProperty("Setting描述")
    private String itemDesc;

    @ApiModelProperty("Setting状态")
    private Integer status;

    public ActivitySetting(ActivitySettingItem item) {
        if (item != null) {
            this.activityKey = item.getActivityKey();
            this.item = item.getItem();
            this.itemValue = item.getItemValue();
            this.itemDesc = item.getItemDesc();
            this.status = item.getStatus();
        }
    }

    public String getActivityKey() {
        return activityKey;
    }

    public String getItem() {
        return item;
    }

    public String getItemValue() {
        return itemValue;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public Integer getStatus() {
        return status;
    }
}
