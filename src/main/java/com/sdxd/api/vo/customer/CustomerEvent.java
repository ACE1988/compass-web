package com.sdxd.api.vo.customer;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

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
public class CustomerEvent {

    @ApiModelProperty(value = "客户ID")
    private String userId;

    @ApiModelProperty(value = "状态, COMPLETE: 结案, SUBMIT: 提交", allowableValues = "COMPLETE,SUBMIT")
    private EventStatus status;

    @ApiModelProperty(value = "接听时间")
    private Date receptionTime;

    @ApiModelProperty(value = "来电号码")
    private String incomingPhone;

    @ApiModelProperty(value = "来电事由",
            allowableValues = "借款问题,还款问题,扣款失败,审核条件,手机相关,资料修改,费用计算,续期问题,逾期问题,账号问题,投诉与建议")
    private String eventType;

    @ApiModelProperty(value = "主要问题",
            allowableValues = "如何申请借款, 借款多久到账")
    private String problemType;

    @ApiModelProperty(value = "内容")
    private String content;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Date getReceptionTime() {
        return receptionTime;
    }

    public void setReceptionTime(Date receptionTime) {
        this.receptionTime = receptionTime;
    }

    public String getIncomingPhone() {
        return incomingPhone;
    }

    public void setIncomingPhone(String incomingPhone) {
        this.incomingPhone = incomingPhone;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}