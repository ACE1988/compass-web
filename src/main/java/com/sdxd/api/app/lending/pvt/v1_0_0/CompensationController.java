package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.PK;
import com.sdxd.api.vo.lending.CompensatoryBatchDetailsQuery;
import com.sdxd.api.vo.lending.CompensatoryBatchQuery;
import com.sdxd.api.vo.lending.CompensatoryBatchSummaryQuery;
import com.sdxd.api.vo.lending.CompensatorySummary;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.PageParameter;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.pay.api.P2PUserDubboService;
import com.sdxd.pay.response.ShengpayMerchantBalance;
import com.sdxd.repayment.dubbo.RepaymentCompensatoryDubboService;
import com.sdxd.repayment.dubbo.request.*;
import com.sdxd.repayment.dubbo.response.CapitalProvidersResponse;
import com.sdxd.repayment.dubbo.response.CompensatoryBatchResponse;
import com.sdxd.repayment.dubbo.response.CompensatoryDetailResponse;
import com.sdxd.repayment.dubbo.response.StatisticsCompensatoryDetailResponse;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.util.List;

import static com.sdxd.api.vo.LendingErrors.EMPTY_BATCH_DETAILS_ID;
import static com.sdxd.api.vo.LendingErrors.EMPTY_BATCH_ID;
import static com.sdxd.common.web.util.ResponseUtil.data;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.vo.RestResponse.fail;
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
 * 2017/9/15     melvin                 Created
 */
@Api(value = "Compensation-API", produces = "application/json")
@Controller
@RequestMapping(value = "/compensation", produces = "application/json")
public class CompensationController {

    private static final Logger log = LoggerFactory.getLogger(CompensationController.class);

    @Reference(version = "1.0.0")
    private RepaymentCompensatoryDubboService repaymentCompensatoryDubboService;

    @Reference(version = "1.0.0")
    P2PUserDubboService p2PUserDubboService;

    @ApiOperation(value = "获取资方列表", notes = "获取资方列表")
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
    @RequestMapping(value = "/capital-providers", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<CapitalProvidersResponse>> getCapitalProviders() {
        DubboResponse<List<CapitalProvidersResponse>> response = repaymentCompensatoryDubboService.queryProvider();
        return rest(response);
    }

    @ApiOperation(value = "查询代偿批次列表", notes = "查询代偿批次列表")
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
    @RequestMapping(value = "/compensatory-batch", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<CompensatoryBatchResponse>> findPages(
            @BeanParam PageParameter page,
            @BeanParam CompensatoryBatchQuery query
    ) {
        QueryComBatchRequest request = new QueryComBatchRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPageSize(page.getPageSize());
        request.setCurrentPage(page.getPageNo());
        request.setCapitalProviderId(query.getCapitalProviderId());
        request.setId(query.getBatchId());
        request.setStatus(query.getCompensatoryStatus());
        request.setStartCreateTime(query.getGenerateFrom());
        request.setEndCreateTime(query.getGenerateTo());
        DubboResponse<PaginationSupport<CompensatoryBatchResponse>> response = repaymentCompensatoryDubboService.queryBatchPages(request);
        return rest(response);
    }

    @ApiOperation(value = "查询代偿批次统计信息", notes = "查询代偿批次统计信息")
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
    @RequestMapping(value = "/compensatory-summary", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<CompensatorySummary> findSummary(
            @Valid @BeanParam CompensatoryBatchSummaryQuery query
    ) {
        ShengpayMerchantBalance merchantBalance = null;
        try {
            DubboResponse<ShengpayMerchantBalance> merchant = p2PUserDubboService.queryMerchantBalance(query.getCapitalProviderId(), "p2pshengPayV2");
            merchantBalance = data(merchant);
        } catch (ProcessBizException e) {
            log.error("", e);
        }

        StatisticsCompensatoryDetailResponse summary = null;
        try {
            StaticsRequest request = new StaticsRequest();
            request.setRequestId(BillNoUtils.GenerateBillNo());
            request.setCapitalProviderId(query.getCapitalProviderId());
            request.setStaticsDate(query.getDay());
            DubboResponse<StatisticsCompensatoryDetailResponse> response = repaymentCompensatoryDubboService.statisticsDetail(request);
            summary = data(response);
        } catch (ProcessBizException e) {
            log.error("", e);
        }
        return ok(new CompensatorySummary(merchantBalance, summary));
    }

    @ApiOperation(value = "查询代偿批次明细列表", notes = "查询代偿批次明细列表")
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
    @RequestMapping(value = "/compensatory-details", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<CompensatoryDetailResponse>> findDetails(
            @BeanParam PageParameter page,
            @BeanParam CompensatoryBatchDetailsQuery query
    ) {
        QueryComDetailRequest request = new QueryComDetailRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setPageSize(page.getPageSize());
        request.setCurrentPage(page.getPageNo());
        request.setBatchId(query.getBatchId());
        request.setApplyId(query.getApplyId());
        request.setStatus(query.getCompensatoryDetailsStatus());
        request.setStartLoanTime(query.getStartLoanTime());
        request.setEndLoanTime(query.getEndLoanTime());
        DubboResponse<PaginationSupport<CompensatoryDetailResponse>> response = repaymentCompensatoryDubboService.queryDetailPages(request);
        return rest(response);
    }

    @ApiOperation(value = "代偿", notes = "代偿")
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
    @RequestMapping(value = "/compensatory-submit", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> submit(@ApiParam(value = "批次号列表", required = true) @RequestBody PK<String> pk) {
        if (pk == null || pk.empty()) {
            return fail(EMPTY_BATCH_ID);
        }
        CompenatorySubmitRequest request = new CompenatorySubmitRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatchIds(pk.getIds());
        DubboResponse<Boolean> response = repaymentCompensatoryDubboService.submit(request);
        return rest(response);
    }

    @ApiOperation(value = "拒绝", notes = "拒绝")
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
    @RequestMapping(value = "/compensatory-reject", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> reject(@ApiParam(value = "批次号列表", required = true) @RequestBody PK<String> pk) {
        if (pk == null || pk.empty()) {
            return fail(EMPTY_BATCH_ID);
        }
        CompenatorySubmitRequest request = new CompenatorySubmitRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBatchIds(pk.getIds());
        DubboResponse<Boolean> response = repaymentCompensatoryDubboService.reject(request);
        return rest(response);
    }

    @ApiOperation(value = "重试", notes = "重试")
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
    @RequestMapping(value = "/compensatory-retry", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> retry(@ApiParam(value = "批次明细号列表", required = true) @RequestBody PK<String> pk) {
        if (pk == null || pk.empty()) {
            return fail(EMPTY_BATCH_DETAILS_ID);
        }
        CompenatoryRetryRequest request = new CompenatoryRetryRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setCompensatoryDetailId(pk.getIds());
        DubboResponse<Boolean> response = repaymentCompensatoryDubboService.retry(request);
        return rest(response);
    }
}
