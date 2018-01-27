package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.api.service.AdminService;
import com.sdxd.api.vo.ProcessResult;
import com.sdxd.api.vo.lending.*;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.rest.RestContext;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.loan.api.LoanDubboService;
import com.sdxd.loan.api.LoanSettingDubboService;
import com.sdxd.loan.enums.LoanStatus;
import com.sdxd.loan.enums.SettingVariable;
import com.sdxd.loan.request.*;
import com.sdxd.loan.response.LoanInfo;
import com.sdxd.loan.response.LoanStatisticInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.Throwables.toResponse;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.lending.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/29     melvin                 Created
 */
@Api(value = "Lending-API", produces = "application/json")
@Controller
@RequestMapping(value = "/lending", produces = "application/json")
public class LendingController {

    @Reference(version = "1.0.0")
    private LoanSettingDubboService loanSettingDubboService;

    @Reference(version = "1.0.0")
    private LoanDubboService loanDubboService;

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "获取放款列表", notes = "获取放款列表")
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
    public RestResponse<PaginationSupport<LoanInfo>> query(
            @Valid @BeanParam LendingQuery query) {
        LoanPageQueryRequest request = new LoanPageQueryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCurrentPage(query.getCurrentPage());
        request.setPageSize(query.getPageSize());
        request.setCardNo(query.getBankCardNo());
        request.setCause(query.getRejectReason());
        request.setEndLoanStart(query.getStartArrivalTime());
        request.setEndLoanEnd(query.getEndArrivalTime());
        request.setAmount(query.getAmount());
        request.setPeriod(query.getPeriod());
        request.setProductCategory(query.getProductCategory());
        request.setUserName(query.getLender());
        request.setCapitalProviderId(query.getCapitalProviderId());
        request.setChannelCode(query.getLendingChannelCode());
        request.setApplyId(query.getContractNo());
        request.setStatus(query.getLendingStatus() == null ? null : query.getLendingStatus().code);
        DubboResponse<PaginationSupport<LoanInfo>> response = loanDubboService.getLoanList(request);
        return rest(response);
    }

    @ApiOperation(value = "获取待放款统计", notes = "获取待放款统计")
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
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<LoanStatisticInfo> getStatistics() {
        LoanStatisticRequest request = new LoanStatisticRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setLoanStatus(LoanStatus.NEW.code);
        DubboResponse<LoanStatisticInfo> response = loanDubboService.statisticLoanData(request);
        return rest(response);
    }

    @ApiOperation(value = "获取是否自动放款", notes = "获取是否自动放款")
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
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Boolean> getLendingSettings() {
        GetLoanSettingRequest request = new GetLoanSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariable(SettingVariable.AUTO_LOAN_SETTING);
        DubboResponse<String> response = loanSettingDubboService.getVariable(request);
        return rest(response, resp -> resp.equals("1"));
    }

    @ApiOperation(value = "设置是否自动放款", notes = "设置是否自动放款")
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
    @RequestMapping(value = "/settings", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse updateLendingSettings(
            @Valid @RequestBody LendingSettings settings
    ) {
        LoanSettingRequest request = new LoanSettingRequest();
        request.setVariable(SettingVariable.AUTO_LOAN_SETTING);
        request.setValue(settings.getAutomaticLending() ? "1" : "0");
        DubboResponse<String> response = loanSettingDubboService.setVariable(request);
        return rest(response);
    }

    @ApiOperation(value = "放款", notes = "放款")
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<LendingResult> start(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody LendingApplication application
    ) {
        try {
            String contextId = RestContext.getContextId();
            Date entryTime = RestContext.getRequestEntry();
            AdminUserResponse admin = adminService.getAdminById(operatorId);
            List<ProcessResult<Serializable>> lending =
                    application.getLendingIds().parallelStream().map(lendingId -> {
                        RestContext.setContextId(contextId, entryTime);
                        LoanRequest loanRequest = new LoanRequest();
                        loanRequest.setLoanId(lendingId);
                        loanRequest.setCapitalProviderId(application.getCapitalProviderId());
                        loanRequest.setOperatorId(operatorId);
                        loanRequest.setOperatorName(admin.getName());
                        DubboResponse<String> response = loanDubboService.loan(loanRequest);
                        RestResponse<String> rest = rest(response);
                        return new ProcessResult<>(lendingId, rest.isSuccessful() ? rest.getContent() : rest);
                    }).collect(Collectors.toList());
            return ok(new LendingResult<>(lending));
        } catch (ProcessBizException e) {
            return e.toResult();
        } catch (RuntimeException e) {
            return toResponse(e);
        }
    }

    @ApiOperation(value = "拒绝放款", notes = "拒绝放款")
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
    @RequestMapping(value = "/rejection", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> reject(
            @ContextParam(value = "principal") String operatorId,
            @Valid @RequestBody LendingRejection rejection
    ) {
        LoanRejectRequest request = new LoanRejectRequest();
        request.setLoanId(rejection.getLendingId());
        request.setRejectReason(rejection.getReason());
        request.setRemarks(rejection.getComment());
        request.setOperatorId(operatorId);
        request.setOperatorName("");
        DubboResponse<String> response = loanDubboService.reject(request);
        return rest(response);
    }

    @ApiOperation(value = "修改放款状态", notes = "修改放款状态")
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
    @RequestMapping(value = "/status", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<String> updateException(
            @Valid @RequestBody ExceptionLending lending
    ) {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setLoanId(lending.getLendingId());
        request.setLoanStatus(lending.getStatus());
        DubboResponse<String> response = loanDubboService.updateExceptionLoanStatus(request);
        return rest(response);
    }


    @ApiOperation(value = "取消借款", notes = "取消借款")
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
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> cancel(
            @Valid @RequestBody LoanCancelExecute loanCancelExecute
    ) {
        LoanCancelRequest request = new LoanCancelRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setLoanId(loanCancelExecute.getLoanId());
        request.setOperatorId(loanCancelExecute.getOperatorId());
        request.setOperatorName(loanCancelExecute.getOperatorName());
        request.setRemarks(loanCancelExecute.getRejectReason());
        request.setRejectReason(loanCancelExecute.getRejectReason());
        DubboResponse<String> response = loanDubboService.cancel(request);
        return rest(response);
    }
}
