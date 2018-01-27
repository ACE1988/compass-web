package com.sdxd.api.vo.customer;

import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
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
public class CustomerEventQuery extends PageParameter {

    @ApiParam(value = "来电号码")
    @QueryParam("incoming_phone")
    private String incomingPhone;

    @ApiParam(value = "来电时间从")
    @QueryParam("incoming_time_from")
    private Date incomingTimeFrom;

    @ApiParam(value = "来电时间到")
    @QueryParam("incoming_time_to")
    private Date incomingTimeTo;

    @ApiParam(value = "客户编号")
    @QueryParam("user_id")
    private String userId;

    @ApiParam(value = "事件编号")
    @QueryParam("event_no")
    private String eventNo;

    @ApiParam(value = "来电事由",
            allowableValues = "借款问题,还款问题,扣款失败,审核条件,手机相关,资料修改,费用计算,续期问题,逾期问题,账号问题,投诉与建议")
    @QueryParam("event_type")
    private String eventType;

    @ApiParam(value = "来电类型, SIGN_UP: 注册用户, NON_SIGN_UP: 非注册用户", allowableValues = "SIGN_UP,NON_SIGN_UP")
    @QueryParam("customer_type")
    private CustomerType customerType;

    @ApiParam(value = "坐席人员")
    @QueryParam("receptionist_id")
    private String receptionistId;

    @ApiParam(value = "坐席人员名字")
    @QueryParam("receptionist")
    private String receptionist;

    @ApiParam(value = "状态, COMPLETE: 结案, SUBMIT: 提交", allowableValues = "COMPLETE,SUBMIT")
    @QueryParam("status")
    private EventStatus status;

    public String getIncomingPhone() {
        return incomingPhone;
    }

    public void setIncomingPhone(String incomingPhone) {
        this.incomingPhone = incomingPhone;
    }

    public Date getIncomingTimeFrom() {
        return incomingTimeFrom;
    }

    public void setIncomingTimeFrom(Date incomingTimeFrom) {
        this.incomingTimeFrom = incomingTimeFrom;
    }

    public Date getIncomingTimeTo() {
        return incomingTimeTo;
    }

    public void setIncomingTimeTo(Date incomingTimeTo) {
        this.incomingTimeTo = incomingTimeTo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(String receptionistId) {
        this.receptionistId = receptionistId;
    }

    public String getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(String receptionist) {
        this.receptionist = receptionist;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
