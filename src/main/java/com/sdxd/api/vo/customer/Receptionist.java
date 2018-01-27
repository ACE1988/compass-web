package com.sdxd.api.vo.customer;

import com.sdxd.admin.dubbo.api.response.OutUserResponse;
import io.swagger.annotations.ApiModelProperty;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.customer
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/9     melvin                 Created
 */
public class Receptionist {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "接电量")
    private Integer receptionCount;

    @ApiModelProperty(value = "事件量")
    private Integer eventCount;

    @ApiModelProperty(value = "在线时长")
    private Long upTime;

    @ApiModelProperty(value = "满意度")
    private Integer satisfaction;

    @ApiModelProperty(value = "云牛帐号")
    private OutUserResponse account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReceptionCount() {
        return receptionCount;
    }

    public void setReceptionCount(Integer receptionCount) {
        this.receptionCount = receptionCount;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Long getUpTime() {
        return upTime;
    }

    public void setUpTime(Long upTime) {
        this.upTime = upTime;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public OutUserResponse getAccount() {
        return account;
    }

    public void setAccount(OutUserResponse account) {
        this.account = account;
    }
}
