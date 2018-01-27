package com.sdxd.api.vo.lending;

import com.sdxd.common.web.vo.PageParameter;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.lending
 * 系统名           ：退款单查询
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/5/09     刘节                 Created
 */
public class RefundOrderQuery extends PageParameter {

    @ApiParam(value = "客户姓名")
    @QueryParam(value = "userName")
    private String userName;

    @ApiParam(value = "手机号码")
    @QueryParam(value = "phone")
    private String phone;

    @ApiParam(value = "申请ID")
    @QueryParam(value = "apply_id")
    private String applyId;

    @ApiParam(value = "还款单ID")
    @QueryParam(value = "document_id")
    private String documentId;

    @ApiParam(
            value = "退款状态, 0:待审核 1:审核中 2:驳回 9:审核通过  11:退款中  12:退款失败  19:退款成功",
            allowableValues = "0,1,2,9,11,12,19")
    @QueryParam(value = "status")
    private Byte status;

    @ApiParam(value = "申请退款时间（开始）")
    @QueryParam(value = "apply_time_from")
    private Date startApplyTime;

    @ApiParam(value = "申请退款时间（结束）")
    @QueryParam(value = "apply_time_to")
    private Date endApplyTime;

    @ApiParam(value = "审核退款时间（开始）")
    @QueryParam(value = "approve_time_from")
    private Date startApproveTime;

    @ApiParam(value = "审核退款时间（结束）")
    @QueryParam(value = "approve_time_to")
    private Date endApproveTime;

    @ApiParam(value = "实际退款时间（开始）")
    @QueryParam(value = "refund_time_from")
    private Date startRefundTime;

    @ApiParam(value = "实际退款时间（结束）")
    @QueryParam(value = "refund_time_to")
    private Date endRefundTime;

    @ApiParam(value = "退款单生成时间（开始）")
    @QueryParam(value = "create_time_from")
    private Date createStartTime;

    @ApiParam(value = "退款单生成时间（结束）")
    @QueryParam(value = "create_time_to")
    private Date createEndTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getStartApplyTime() {
        return startApplyTime;
    }

    public void setStartApplyTime(Date startApplyTime) {
        this.startApplyTime = startApplyTime;
    }

    public Date getEndApplyTime() {
        return endApplyTime;
    }

    public void setEndApplyTime(Date endApplyTime) {
        this.endApplyTime = endApplyTime;
    }

    public Date getStartApproveTime() {
        return startApproveTime;
    }

    public void setStartApproveTime(Date startApproveTime) {
        this.startApproveTime = startApproveTime;
    }

    public Date getEndApproveTime() {
        return endApproveTime;
    }

    public void setEndApproveTime(Date endApproveTime) {
        this.endApproveTime = endApproveTime;
    }

    public Date getStartRefundTime() {
        return startRefundTime;
    }

    public void setStartRefundTime(Date startRefundTime) {
        this.startRefundTime = startRefundTime;
    }

    public Date getEndRefundTime() {
        return endRefundTime;
    }

    public void setEndRefundTime(Date endRefundTime) {
        this.endRefundTime = endRefundTime;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }
}
