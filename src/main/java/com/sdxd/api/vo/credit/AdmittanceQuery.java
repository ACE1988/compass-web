package com.sdxd.api.vo.credit;

import com.sdxd.approve.dubbo.api.enums.PageOrderField;
import com.sdxd.common.web.vo.RestRequest;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;

import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/24     melvin                 Created
 */
public class AdmittanceQuery extends RestRequest {

    @ApiParam(value = "准入申请编号")
    @QueryParam(value = "admittance_id")
    private String admittanceId;

    @ApiParam(value = "客户ID")
    @QueryParam(value = "customer_id")
    private List<String> userIds;

    @ApiParam(value = "客户名")
    @QueryParam(value = "customer_name")
    private String userName;

    @ApiParam(value = "客户手机")
    @QueryParam(value = "phone")
    private String phone;

    @ApiParam(value = "城市")
    @QueryParam(value = "city")
    private String city;

    @ApiParam(value = "客户类型, 0-新户 1-旧户", allowableValues = "0,1")
    @QueryParam(value = "newbie")
    private Integer newbie;

    @ApiParam(value = "产品ID")
    @QueryParam(value = "product_id")
    private String productId;

    @ApiParam(value = "产品类型, 小额单期: small_one_period", allowableValues = "small_one_period")
    @QueryParam(value = "product_category")
    private String productCategory;

    @ApiParam(value = "申请金额", allowableValues = "500,1000,2000")
    @QueryParam(value = "amount")
    private Integer amount;

    @ApiParam(value = "申请期限", allowableValues = "7,14")
    @QueryParam(value = "period")
    private Integer period;

    @ApiParam(value = "初审人ID")
    @QueryParam(value = "assignee_id")
    private List<String> assigneeIds;

    @ApiParam(value = "初审人名字")
    @QueryParam(value = "assignee_name")
    private String assigneeName;

    @ApiParam(value = "终审人ID")
    @QueryParam(value = "leader_id")
    private String leaderId;

    @ApiParam(value = "终审人名字")
    @QueryParam(value = "leader_name")
    private String leaderName;

    @ApiParam(
            value = "审查状态, " +
                    "<ul><li>初审: 1-待初审, 4-暂存, 17-终审退回</li><li>终审: 2-初审通过, 3-初审拒绝</li><li>结案：18-结案拒绝, 19-结案通过</li></ul>" +
                    "<br>所有状态: 0-待分配 1-待初审 2-初审通过 3-初审拒绝 4-暂存 17-终审退回 18-结案拒绝 19-结案通过",
            allowableValues = "0,1,2,3,4,17,18,19")
    @QueryParam(value = "status")
    private List<Integer> status;

    @ApiParam(value = "预发结果", allowableValues = "A,D,R")
    @QueryParam(value = "decision_result")
    private String decisionResult;

    @ApiParam(value = "申请时间(起始), 默认为上一个月的今天")
    @QueryParam(value = "apply_time_start")
    private Date startApplyTime;

    @ApiParam(value = "申请时间(结束), 默认为今天")
    @QueryParam(value = "apply_time_end")
    private Date endApplyTime;

    @ApiParam(value = "结案时间(起始), 默认为上一个月的今天")
    @QueryParam(value = "closing_time_start")
    private Date startFinalApproveTime;

    @ApiParam(value = "结案时间(结束), 默认为今天")
    @QueryParam(value = "closing_time_end")
    private Date endFinalApproveTime;

    @ApiParam(value = "注册来源")
    @QueryParam(value = "sign_up_source")
    private List<String> signUpSource;

    @ApiParam(value = "申请渠道", allowableValues = "APP,H5")
    @QueryParam(value = "apply_channel")
    private List<String> applyChannel;

    @ApiParam(value = "排序", allowableValues = "finalApproveTime,firstApproveTime,decisionTime,assignTime,applyTime")
    @QueryParam(value = "order")
    private PageOrderField order;

    @ApiParam(value = "页编号")
    @QueryParam(value = "page_no")
    private Integer currentPage;

    @ApiParam(value = "每页条数")
    @QueryParam(value = "page_size")
    private Integer pageSize;

    @ApiParam(value = "专案代码")
    @QueryParam(value = "policy_name")
    private String policyName;

    public boolean noParameters() {
        return StringUtils.isBlank(admittanceId) &&
                (userIds == null || userIds.isEmpty()) &&
                StringUtils.isBlank(userName) &&
                StringUtils.isBlank(phone) &&
                StringUtils.isBlank(city) &&
                newbie == null &&
                StringUtils.isBlank(productId) &&
                StringUtils.isBlank(productCategory) &&
                amount == null &&
                period == null &&
                (assigneeIds == null || assigneeIds.isEmpty()) &&
                StringUtils.isBlank(assigneeName) &&
                StringUtils.isBlank(leaderId) &&
                StringUtils.isBlank(leaderName) &&
                (status == null || status.isEmpty()) &&
                StringUtils.isBlank(decisionResult) &&
                startApplyTime == null &&
                endApplyTime == null &&
                startFinalApproveTime == null &&
                endFinalApproveTime == null &&
                (signUpSource == null || signUpSource.isEmpty()) &&
                (applyChannel == null || applyChannel.isEmpty()) &&
                StringUtils.isBlank(policyName);
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getAdmittanceId() {
        return admittanceId;
    }

    public void setAdmittanceId(String admittanceId) {
        this.admittanceId = admittanceId;
    }

    public List<Long> getUserIds() {
        if (userIds != null) {
            return Observable.from(userIds).map(Long::parseLong).toList().toBlocking().first();
        }
        return null;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getNewbie() {
        return newbie;
    }

    public void setNewbie(Integer newbie) {
        this.newbie = newbie;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public List<String> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(List<String> assigneeIds) {
        if (assigneeIds != null) {
            this.assigneeIds = assigneeIds.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        }
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public String getDecisionResult() {
        return decisionResult;
    }

    public void setDecisionResult(String decisionResult) {
        this.decisionResult = decisionResult;
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

    public Date getStartFinalApproveTime() {
        return startFinalApproveTime;
    }

    public void setStartFinalApproveTime(Date startFinalApproveTime) {
        this.startFinalApproveTime = startFinalApproveTime;
    }

    public Date getEndFinalApproveTime() {
        return endFinalApproveTime;
    }

    public void setEndFinalApproveTime(Date endFinalApproveTime) {
        this.endFinalApproveTime = endFinalApproveTime;
    }

    public List<String> getSignUpSource() {
        return signUpSource;
    }

    public void setSignUpSource(List<String> signUpSource) {
        this.signUpSource = signUpSource;
    }

    public List<String> getApplyChannel() {
        return applyChannel;
    }

    public void setApplyChannel(List<String> applyChannel) {
        this.applyChannel = applyChannel;
    }

    public PageOrderField getOrder() {
        return order;
    }

    public void setOrder(PageOrderField order) {
        this.order = order;
    }

    public Integer getCurrentPage() {
        return currentPage == null ? 1 : currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize == null ? 15 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
