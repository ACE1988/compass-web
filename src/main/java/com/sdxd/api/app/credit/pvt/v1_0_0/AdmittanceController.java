package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.sdxd.admin.dubbo.api.AdminUserGroupDubboService;
import com.sdxd.admin.dubbo.api.request.QueryGroupRequest;
import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.api.service.*;
import com.sdxd.api.vo.credit.*;
import com.sdxd.approve.dubbo.api.DistributionUserService;
import com.sdxd.approve.dubbo.api.PagingQuery;
import com.sdxd.approve.dubbo.api.request.DistributionRequest;
import com.sdxd.approve.dubbo.api.request.QueryApproveRequest;
import com.sdxd.approve.dubbo.api.response.ApproveApplicationResponse;
import com.sdxd.approve.dubbo.api.response.DistributionResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.security.Restrict;
import com.sdxd.common.web.security.Subject;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.response.pojo.XDChannelBO;
import com.sdxd.data.dubbo.api.risk.user.ImageCompareDataDubboService;
import com.sdxd.data.dubbo.api.risk.user.dto.FaceVerifyResultRequest;
import com.sdxd.data.dubbo.api.risk.user.dto.FaceVerifyResultResponse;
import com.sdxd.decision.api.DecisionService;
import com.sdxd.decision.api.response.RiskHintDetailInfo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.util.List;

import static com.sdxd.api.vo.CreditErrors.NO_PARAMETERS;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.Throwables.toResponse;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.ErrorCode.ResourceError.CAN_NOT_ACCESS;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admittance.pvt
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/23     melvin                 Created
 */
@Api(value = "Admittance-API", produces = "application/json")
@Controller
@RequestMapping(value = "/admittance", produces = "application/json")
public class AdmittanceController {

    private static final Logger log = LoggerFactory.getLogger(AdmittanceController.class);

    @Reference(version = "1.0.0")
    private DistributionUserService distributionUserService;

    @Reference(version = "1.0.0")
    private PagingQuery pagingQuery;

    @Reference(version = "1.0.0")
    private ImageCompareDataDubboService imageCompareDataDubboService;

    @Reference(version = "1.0.0")
    private DecisionService decisionService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RiskHintService riskHintService;

    @Autowired
    private ChannelResourceService channelResourceService;
    @Reference(version = "1.0.0")
    private AdminUserGroupDubboService adminUserGroupDubboService;

    @ApiOperation(value = "查询准入申请", notes = "查询准入申请")
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
    @Restrict
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<ApproveApplicationResponse>> search(
            @ContextParam(value = "subject") Subject subject,
            @Valid @BeanParam AdmittanceQuery query) {
        if (query.noParameters()) {
            return fail(NO_PARAMETERS);
        }
        AdmittanceAccess access = AdmittanceAccess.of(subject);
        if (query.getUserIds() == null || query.getUserIds().size() == 0) {
            boolean authorized = access.authorized(query.getStatus());
            if (!authorized) {
                return fail(CAN_NOT_ACCESS);
            }
        }
        QueryApproveRequest request = new QueryApproveRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAdmittanceId(query.getAdmittanceId());
        request.setCity(query.getCity());
        request.setUserIds(query.getUserIds());
        request.setUserName(query.getUserName());
        request.setPhone(query.getPhone());
        request.setNewbie(query.getNewbie());
        request.setProductId(query.getProductId());
        request.setProductCategory(query.getProductCategory());
        request.setAmount(query.getAmount());
        request.setPeriod(query.getPeriod());
        request.setAssigneeIds(query.getAssigneeIds());
        request.setAssigneeName(query.getAssigneeName());
        request.setStatus(query.getStatus());
        request.setDecisionResult(query.getDecisionResult());
        request.setStartApplyTime(query.getStartApplyTime());
        request.setEndApplyTime(query.getEndApplyTime());
        request.setLeaderId(query.getLeaderId());
        request.setLeaderName(query.getLeaderName());
        request.setStartFinalApproveTime(query.getStartFinalApproveTime());
        request.setEndFinalApproveTime(query.getEndFinalApproveTime());
        request.setSources(query.getSignUpSource());
        request.setChannels(query.getApplyChannel());
        request.setOrderField(query.getOrder());
        request.setCurrentPage(query.getCurrentPage());
        request.setPageSize(query.getPageSize());
        request.setPolicyName(query.getPolicyName());//添加了专案代码

        if (query.getUserIds() == null || query.getUserIds().size() == 0) {
            access.restrict(id -> request.setAssigneeIds(Lists.newArrayList(id)));
        }
        boolean isFinal = access.isGroupingRole(query.getStatus());
        if(isFinal){
            List<String> current = query.getAssigneeIds();
            String leaderId = subject.getValue();
            QueryGroupRequest qrequest = new QueryGroupRequest();
            qrequest.setRequestId(BillNoUtils.GenerateBillNo());
            qrequest.setOperatorId(leaderId);
            DubboResponse<List<String>> group = adminUserGroupDubboService.queryGroupByLeaderId(qrequest);
            if(group != null && group.getData() != null){
                List<String> all = Lists.newArrayList(group.getData());
                if (access.isMember()) {
                    all.add(subject.getValue());
                }

                if (current == null || current.isEmpty()) {
                    request.setAssigneeIds(group.getData());
                }
                if (current != null && !all.containsAll(current)) {
                    return ok();
                }
            }else{
                return fail(CAN_NOT_ACCESS);
            }
        }

        log.debug("After restrict access, user {}, assignee is {}", subject.getValue(), request.getAssigneeIds());
        DubboResponse<PaginationSupport<ApproveApplicationResponse>> response = pagingQuery.queryOnPage(request);
        return rest(response);
    }

    @ApiOperation(value = "分配准入申请", notes = "分配准入申请")
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
    @RequestMapping(value = "/assignment", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<DistributionResponse> assign(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody AdmittanceAssignment assignment) {
        try {
            AdminUserResponse admin = adminService.getAdminById(assignment.getApproverId());
            DistributionRequest request = new DistributionRequest();
            request.setRequestId(BillNoUtils.GenerateBillNo());
            request.setUserId(assignment.getApproverId());
            request.setUserName(admin.getName());
            request.setSystemUserId(operatorId);
            request.setIds(assignment.getAdmittanceIds());
            DubboResponse<DistributionResponse> response = distributionUserService.distributePersonnel(request);
            return rest(response);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "查询数据比对度", notes = "查询数据比对度")
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
    @RequestMapping(value = "/comparision", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<FaceVerifyResultResponse> getComparision(
            @Valid @BeanParam Admittance admittance) {
        FaceVerifyResultRequest request = new FaceVerifyResultRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAdmittanceId(admittance.getAdmittanceId());
        DubboResponse<FaceVerifyResultResponse> response = imageCompareDataDubboService.queryFaceVerifyResult(request);
        return rest(response);
    }

    @ApiOperation(value = "获取决策规则命中信息", notes = "获取决策规则命中信息")
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
    @RequestMapping(value = "/{id}/rules-hit", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<RiskHint>> getRulesHit(
            @ApiParam(value = "准入申请编号", required = true) @PathVariable(value = "id") String admittanceId
    ) {
        try {
            List<RiskHintDetailInfo> hints = riskHintService.getRiskHintDetails(admittanceId);
            List<RiskHint> riskHints = Observable.from(hints).map(RiskHint::new).toList().toBlocking().first();
//                hints.stream().
//                        map((RiskHint::new)).
//                        map(hint -> {
//                    RiskHintDetailInfo details = propagate(() -> riskHintService.getRiskHintDetails(hint.getCode(), admittanceId));
//                    return hint.extract(details.getDetail());
//                }).collect(Collectors.toList());
            return ok(riskHints);
        } catch (ProcessBizException e) {
            return e.toResult();
        } catch (RuntimeException e) {
            return toResponse(e);
        }
    }

//    @ApiOperation(value = "获取决策规则命中信息", notes = "获取决策规则命中信息")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = "Authorization",
//                    value = "用户Token",
//                    dataType = "string",
//                    paramType = "header",
//                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
//                    required = true)
//    })
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
//            @ApiResponse(code = 500, message = "服务器不能完成请求")
//    })
//    @RequestMapping(value = "/{id}/rules-hit", method = RequestMethod.GET)
//    @ResponseBody
//    public RestResponse<List<RuleInfo>> getRulesHitOld(
//            @ApiParam(value = "准入申请编号", required = true) @PathVariable(value = "id") String admittanceId
//    ) {
//        RulesHitRequest request = new RulesHitRequest();
//        request.setRequestId(BillNoUtils.GenerateBillNo());
//        request.setSourceId(admittanceId);
//        DubboResponse<List<RuleInfo>> response = decisionService.queryRulesHit(request);
//        return rest(response);
//    }

    @ApiOperation(value = "注册来源查询", notes = "注册来源查询")
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
    @RequestMapping(value = "/channel-resource", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<XDChannelBO>> getChanelResources(
            @BeanParam ChannelResourceQuery query
    ) {
        try {
            PaginationSupport<XDChannelBO> page =
                    forValue(new PaginationSupport<XDChannelBO>(null, 0)).
                            parallel(() -> channelResourceService.getChannelResource(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> channelResourceService.countChannelResource(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
