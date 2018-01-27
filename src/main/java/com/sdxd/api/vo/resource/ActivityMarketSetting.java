package com.sdxd.api.vo.resource;

import com.google.common.collect.Lists;
import com.sdxd.data.dubbo.api.response.CmsSuspensionSettingListResponse;
import com.sdxd.data.dubbo.api.response.pojo.CmsSuspensionSettingVo;

import java.util.List;


public class ActivityMarketSetting {


    private List<CmsSuspensionSettingVo> cmsSuspensionSettingVoList = Lists.newArrayList();

    private List<Activity> activities = Lists.newArrayList();

    private String withdrawAmount;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<CmsSuspensionSettingVo> getCmsSuspensionSettingVoList() {
        return cmsSuspensionSettingVoList;
    }

    public void setCmsSuspensionSettingVoList(List<CmsSuspensionSettingVo> cmsSuspensionSettingVoList) {
        this.cmsSuspensionSettingVoList = cmsSuspensionSettingVoList;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }
}
