package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.credit.ApprovalStash;
import com.sdxd.api.vo.credit.CreditApproval;
import com.sdxd.api.vo.credit.CreditApprovalReturn;
import com.sdxd.approve.dubbo.api.ApproveAuditeDubboService;
import com.sdxd.approve.dubbo.api.enums.CreditStatus;
import com.sdxd.approve.dubbo.api.request.AuditRequest;
import com.sdxd.approve.dubbo.api.response.AuditResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Collections;

import static com.sdxd.common.web.util.ResponseUtil.rest;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.credit.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/24     melvin                 Created
 */
@Api(value = "Credit-Line-API", produces = "application/json")
@Controller
@RequestMapping(value = "/credit-line", produces = "application/json")
public class CreditApprovalController {

    @Reference(version = "1.0.0")
    private ApproveAuditeDubboService approveAuditeDubboService;

    @ApiOperation(value = "暂存", notes = "暂存")
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
    @RequestMapping(value = "/stash", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<AuditResponse> createStash(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody ApprovalStash stash
    ) {
        AuditRequest request = new AuditRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(Collections.singletonList(stash.getAdmittanceId()));
        request.setApproverId(operatorId);
        request.setStatus(CreditStatus.TEMPORARY.getValue());
        DubboResponse<AuditResponse> response = approveAuditeDubboService.auditeFirst(request);
        return rest(response);
    }

    @ApiOperation(value = "初审提交", notes = "初审提交")
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
    @RequestMapping(value = "/first-approval", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse firstApprove(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody CreditApproval approval
    ) {
        AuditRequest request = new AuditRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(approval.getAdmittanceIds());
        request.setApproverId(operatorId);
        request.setStatus(approval.getResult().getFirstApprovalCode());
        request.setRejectCode(approval.getRejectCode());
        request.setRejectCause(approval.getRejectReason());
        request.setRemarks(approval.getComment());
        DubboResponse<AuditResponse> response = approveAuditeDubboService.auditeFirst(request);
        return rest(response);
    }

    @ApiOperation(value = "终审提交", notes = "终审提交")
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
    @RequestMapping(value = "/final-approval", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse finalApproval(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody CreditApproval approval
    ) {
        AuditRequest request = new AuditRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(approval.getAdmittanceIds());
        request.setApproverId(operatorId);
        if (!approval.isMultipleSelection()) {
            request.setStatus(approval.getResult().getFinalApprovalCode());
        }
        request.setRejectCause(approval.getRejectReason());
        request.setRemarks(approval.getComment());
        request.setIsBatch(approval.getBatch());
        DubboResponse<AuditResponse> response = approveAuditeDubboService.auditeFinal(request);
        return rest(response);
    }

    @ApiOperation(value = "终审退回", notes = "终审退回")
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
    @RequestMapping(value = "/approval-return", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse increaseCreditLine(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody CreditApprovalReturn approvalReturn
    ) {
        AuditRequest request = new AuditRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(Collections.singletonList(approvalReturn.getAdmittanceId()));
        request.setApproverId(operatorId);
        request.setStatus(CreditStatus.FINAL_RETURN.getValue());
        request.setRejectCause(approvalReturn.getRejectReason());
        request.setRemarks(approvalReturn.getComment());
        DubboResponse<AuditResponse> response = approveAuditeDubboService.auditeFinal(request);
        return rest(response);
    }
}
