package com.sdxd.api.app.customer.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.sdxd.admin.dubbo.api.SystemVariableDubboService;
import com.sdxd.admin.dubbo.api.request.VariableCodeRequest;
import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.admin.dubbo.api.response.RolePermissionResponse;
import com.sdxd.admin.dubbo.api.response.RolePermissionVo;
import com.sdxd.admin.dubbo.api.response.SystemVariableResponse;
import com.sdxd.api.service.AdminService;
import com.sdxd.api.service.CustomerEventService;
import com.sdxd.api.service.LoanService;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.vo.customer.Customer;
import com.sdxd.api.vo.customer.CustomerBase;
import com.sdxd.api.vo.customer.CustomerEvent;
import com.sdxd.api.vo.customer.EventStatus;
import com.sdxd.apply.response.ApplyAdmittanceDetailResponse;
import com.sdxd.apply.response.ApplyMoneyInfo;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.security.Subject;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.customer.dubbo.api.response.CustomerEventsCreateResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.repayment.dubbo.RepaymentDocumentDubboService;
import com.sdxd.repayment.dubbo.RepaymentPlanDetailDubboService;
import com.sdxd.repayment.dubbo.RepaymentPlanDubboService;
import com.sdxd.repayment.dubbo.request.DocumentRequest;
import com.sdxd.repayment.dubbo.request.PageRepaymentRequest;
import com.sdxd.repayment.dubbo.request.RepaymentPlanDetailRequest;
import com.sdxd.repayment.dubbo.response.DetailResponse;
import com.sdxd.repayment.dubbo.response.DocumentResponse;
import com.sdxd.repayment.dubbo.response.RepaymentInfo;
import com.sdxd.repayment.dubbo.response.RepaymentPlanInfoExt;
import com.sdxd.user.api.UserQueryService;
import com.sdxd.user.api.response.UserInfo;
import com.sdxd.user.api.response.UserProfileIdentityInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BeanParam;
import java.util.*;
import java.util.stream.Collectors;

import static com.sdxd.api.vo.CreditErrors.*;
import static com.sdxd.common.web.util.ResponseUtil.data;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.customer.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/9     melvin                 Created
 */
@Api(value = "Customer-API", produces = "application/json")
@Controller
@RequestMapping(value = "/customers", produces = "application/json")
public class CustomerController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Reference(version = "1.0.0")
    private RepaymentPlanDubboService repaymentPlanDubboService;

    @Reference(version = "1.0.0")
    private RepaymentPlanDetailDubboService repaymentPlanDetailDubboService;

    @Reference(version = "1.0.0")
    private RepaymentDocumentDubboService repaymentDocumentDubboService;
    @Autowired
    private CustomerEventService customerEventService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private AdminService adminService;

    @Reference(version = "1.0.0")
    private UserQueryService userQueryService;

    @Reference(version = "1.0.0")
    private SystemVariableDubboService systemVariableDubboService;

    @ApiOperation(value = "获取客户ID", notes = "获取客户ID")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<CustomerBase> getReceptionist(
            @ContextParam(value = "principal") String operatorId,
            @ApiParam(value = "客户电话", required = true)
            @RequestParam("phone") String phone
    ) {
        try {
            Long id = profileService.getUserId(phone);
            UserInfo userInfo = profileService.getUserInfo(id);
            return ok(new CustomerBase(
                    String.valueOf(userInfo.getUserId()),
                    userInfo.getPhone(),
                    userInfo.getStatusEnum(),
                    userInfo.getUserProfileIdentityInfo()));
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取客户信息", notes = "获取客户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Customer> getReceptionist(
            @ContextParam(value = "principal") String operatorId,
            @ApiParam(value = "客户ID", required = true)
            @PathVariable("id") Long customerId
    ) {
        try {
            Customer lender =
                    forValue(new Customer(), false).
                            parallel(() -> profileService.getUserInfo(customerId), (customer, user) -> {
                                UserProfileIdentityInfo profile = user.getUserProfileIdentityInfo();
                                customer.setName(profile == null ? null : user.getUserProfileIdentityInfo().getName());
                                customer.setSex(profile == null ? null : user.getUserProfileIdentityInfo().getSex());
                                customer.setCellphone(user.getPhone());
                            }).
                            parallel(() -> profileService.getUserRegInfoInfo(customerId), (customer, userRegInfoInfo) -> {
                                //注册渠道
                                if (null != userRegInfoInfo) {
                                    customer.setRegisterChannel(userRegInfoInfo.getSource());
                                }
                            })
                            .parallel(() -> profileService.hasAccountCode(customerId), (customer, bool) -> {
                                //判断是否有码
                                customer.setHasAccountCode(bool);
                            }).
                            parallel(() -> loanService.getAdmittanceApplicationByCustomer(customerId), (customer, admittance) -> {
                                if (admittance != null) {
                                    Date applyTime = admittance.getCreateTime();
                                    customer.setApplyTime(applyTime);
                                    if (null != admittance.getChannel()) {
                                        customer.setApplyChannel(admittance.getChannel());//添加申请渠道
                                    }
                                    if (null != admittance.getId()) {
                                        customer.setApplyAdmittanceId(admittance.getId());//添加申请编号
                                    }
                                }
                            }).
//                            parallel(() -> loanService.getAdmittanceByCustomer(customerId), (customer, admittance) -> {
//                                if (admittance != null) {
//                                    Integer status = admittance.getStatus();
//                                    customer.setReviewStatus(status);
//                                    customer.setFinalApproveTime(admittance.getFinalApproveTime());
//                                }
//                            }).
                            parallel(() -> profileService.getCertificationQualification(customerId), Customer::setCompletion).
                            parallel(() -> loanService.getInLoanCount(customerId), Customer::setInLoanCount).
                            parallel(() -> loanService.getCreditLine(customerId), Customer::setCreditLine).
                            parallel(() -> loanService.getRepaymentPlan(customerId), (customer, repaymentInfo) -> {
                                RepaymentPlanInfoExt plan = repaymentInfo == null ? null : repaymentInfo.getRepaymentPlanInfoExt();
                                customer.setRepayableAmount(plan == null ? null : plan.getLeftAmount());
                                customer.calculateOverDue(plan);
                            }).
                            dep(Customer::getApplyAdmittanceId, applyAdmittanceId -> loanService.getCustomerStatus(applyAdmittanceId), (customer, admittance) -> {
                                if (admittance != null) {
                                    Integer status = admittance.getStatus();
                                    customer.setReviewStatus(status);
                                    customer.setFinalApproveTime(admittance.getFinalApproveTime());
                                }
                            }).
                            start();
            lender.setUserId(String.valueOf(customerId));
            return ok(lender);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取客户准入申请列表", notes = "获取客户准入申请列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/{id}/admittance", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<ApplyAdmittanceDetailResponse> findLoans(
            @ApiParam(value = "客户ID", required = true)
            @PathVariable("id") Long customerId
    ) {
        try {
            ApplyAdmittanceDetailResponse admittance = loanService.getAdmittanceApplicationByCustomer(customerId);
            return ok(admittance);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取客户申请列表", notes = "获取客户申请列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/{id}/loans", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<ApplyMoneyInfo>> findLoans(
            @ApiParam(value = "客户ID", required = true)
            @PathVariable("id") Long customerId,
            @BeanParam PageParameter pageParameter
    ) {
        try {
            PaginationSupport<ApplyMoneyInfo> page = loanService.findLoanInfoByCustomer(
                    customerId, pageParameter.getPageNo(), pageParameter.getPageSize());
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取客户还款记录", notes = "获取客户还款记录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/{id}/repayments", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<RepaymentPlanInfoExt>> findRepayments(
            @ApiParam(value = "客户ID", required = true)
            @PathVariable("id") Long customerId,
            @ApiParam(value = "状态, 正在还款中：30-待还款；已结清：89-还款成功", required = true, allowableValues = "30,89")
            @RequestParam("status") Integer status,
            @BeanParam PageParameter page
    ) {
        PageRepaymentRequest request = new PageRepaymentRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(customerId);
        request.setCurrentPage(page.getPageNo());
        request.setPageSize(page.getPageSize());
        request.setStatus(Lists.newArrayList(status));
        DubboResponse<PaginationSupport<RepaymentPlanInfoExt>> response = repaymentPlanDubboService.getRepaymentPage(request);
        return rest(response);
    }

    @ApiOperation(value = "获取客户还款息费详情", notes = "获取客户还款息费详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/{id}/repayments/{plan_id}/charges", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<DetailResponse> getRepaymentCharges(
            @ApiParam(value = "客户ID", required = true)
            @PathVariable("id") Long customerId,
            @ApiParam(value = "还款计划ID", required = true)
            @PathVariable("plan_id") String planId
    ) {
        RepaymentPlanDetailRequest request = new RepaymentPlanDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPlanId(planId);
        DubboResponse<DetailResponse> response = repaymentPlanDetailDubboService.getDetailsMergeMergeSameSubject(request);
        return rest(response);
    }

    @ApiOperation(value = "获取客户还款明细", notes = "获取客户还款明细")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/{id}/repayments/{plan_id}", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<DocumentResponse> getRepayment(
            @ApiParam(value = "客户ID", required = true)
            @PathVariable("id") Long customerId,
            @ApiParam(value = "还款计划ID", required = true)
            @PathVariable("plan_id") String planId,
            @ApiParam(value = "申请ID", required = true)
            @RequestParam("apply_id") String applyId
    ) {
        DocumentRequest request = new DocumentRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(customerId);
        request.setPlanId(planId);
        request.setApplyId(applyId);
        DubboResponse<DocumentResponse> response = repaymentDocumentDubboService.getDocument(request);
        return rest(response);
    }


    @ApiOperation(value = "销户", notes = "销户")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse unregisterUser(
            @ContextParam(value = "principal") String operatorId,
            @ApiParam(value = "手机号", required = true)
            @RequestParam(value = "phone") String phone,
            @ApiParam(value = "身份证后六位", required = true)
            @RequestParam(value = "identity_card", required = true) String identityCard,
            @ApiParam(value = "销户原因", required = true)
            @RequestParam(value = "reason", required = false) String reason,
            @ApiParam(value = "来电事由", required = true)
            @RequestParam(value = "event_type") String eventType,
            @ApiParam(value = "主要问题", required = true)
            @RequestParam(value = "problem_type") String problemType
    ) {
        try {
//          权限限制
            RolePermissionResponse rolePermissionResponse = adminService.getRolesByAdminId(operatorId);
            boolean flag  =
                    rolePermissionResponse.getPer()
                    .stream()
                    .map(RolePermissionVo::getRoleId)
                    .anyMatch("KF_MANAGER"::equals);
            if (!flag) {
                return fail(NO_ACCESS);
            }

            Long userId = profileService.getUserId(phone);
            if (userId == null) {
                return fail(LENDER_NOT_FOUND);
            }
//            匹配身份证后六位
            UserInfo userInfo = profileService.getUserInfo(userId);
            UserProfileIdentityInfo userProfileIdentityInfo = userInfo.getUserProfileIdentityInfo();
            if (userProfileIdentityInfo == null) {
                return fail(LENDER_USER_NOT_IDENTITY);
            }
            String idCard = userProfileIdentityInfo.getIdcard();
            log.info("身份证{}", idCard);
            idCard = idCard.substring(idCard.length() - 6, idCard.length());
            if (!StringUtils.trim(identityCard).equals(idCard)) {
                return fail(ERROR_IDENTITY_CARD);
            }
//            有借款未还清
            RepaymentInfo repaymentInfo = loanService.getRepaymentPlan(userId);
            if (repaymentInfo != null) {
                return fail(LOAN_CLEARING_EXIST);
            }
            log.info("开始对{}进行销户", userId);
            DubboResponse<Boolean> unregisterResponse = profileService.unRegister(userId);
            Boolean result = data(unregisterResponse);
            if (result == null || !result) {
                return ok(false);
            }
//            加入电话黑名单
            Boolean response = profileService.addPhoneBlack(phone);
            if (response) {
//            创建客服事件
                log.info("创建客服事件");
                AdminUserResponse admin = adminService.getAdminById(operatorId);
                CustomerEvent event = new CustomerEvent();
                event.setUserId(String.valueOf(userId));
                event.setStatus(EventStatus.SUBMIT);
                event.setReceptionTime(new Date());
                event.setIncomingPhone(phone);
                event.setEventType(eventType);
                event.setProblemType(problemType);
                event.setContent(reason);
                CustomerEventsCreateResponse res = customerEventService.createEvent(event, userInfo, admin, operatorId);
                return ok(res);
            }
            return ok();
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

}
