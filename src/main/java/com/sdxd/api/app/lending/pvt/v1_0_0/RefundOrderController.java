package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.sdxd.api.service.RefundOrderService;
import com.sdxd.api.vo.lending.*;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.repayment.dubbo.response.DocumentSummaryResponse;
import com.sdxd.repayment.dubbo.response.RefundmentInfo;
import com.sdxd.repayment.dubbo.response.RepaymentPlanInfo;
import com.sdxd.repayment.dubbo.response.RepaymentPlanSummary;
import com.sdxd.user.api.response.BindCardInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.BeanParam;


import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.lending.pvt.v1_0_0
 * 系统名           ：退款单管理
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/5/09     刘节                 Created
 */
@Api(value = "Refund-Order-API", produces = "application/json")
@Controller
@RequestMapping(value = "/refund", produces = "application/json")
public class RefundOrderController {

    @Autowired
    private RefundOrderService refundOrderService;

    @ApiOperation(value = "退款单查询", notes = "退款单查询")
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
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public  RestResponse<PaginationSupport<RefundmentInfo>> queryRefundOrder(
            @BeanParam RefundOrderQuery query
    ) {

        try {
            PaginationSupport<RefundmentInfo> page =
                    forValue(new PaginationSupport<RefundmentInfo>(null, 0)).
                            parallel(() -> refundOrderService.refundOrder(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> refundOrderService.refundOrderCount(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "还款计划ID查询还款计划", notes = "还款计划ID查询还款计划")
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
    @RequestMapping(value = "/repayment-plan-info", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<RepaymentPlanInfo> repaymentPlanInfo(
            @BeanParam RepaymentPlanQuery query
    ) {
        DubboResponse<RepaymentPlanInfo> response = refundOrderService.getById(query);
        return rest(response);
    }

    @ApiOperation(value = "获取还款计划的总结信息", notes = "获取还款计划的总结信息")
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
    @RequestMapping(value = "/repayment-plan-summary", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<RepaymentPlanSummary> planSummary(
            @BeanParam RepaymentPlanQuery query
    ) {
        DubboResponse<RepaymentPlanSummary> response = refundOrderService.getPlanSummary(query);
        return rest(response);
    }

    @ApiOperation(value = "退款审核", notes = "退款审核")
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
    @RequestMapping(value = "/refund-approve", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<Boolean> refundApprove(
            @BeanParam ApproveQuery query
    ) {
        DubboResponse<Boolean> bo = refundOrderService.refundApprove(query);

        return rest(bo);
    }

    @ApiOperation(value = "退款单驳回", notes = "退款单驳回")
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
    @RequestMapping(value = "/refund-reject", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<Boolean> refundReject(
            @BeanParam RejectQuery query
    ) {
        DubboResponse<Boolean> d = refundOrderService.refundReject(query);
        return rest(d);
    }

    @ApiOperation(value = "银行卡号查询", notes = "银行卡查询")
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
    @RequestMapping(value = "/bank-cord", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<BindCardInfo>> refundReject(
            @BeanParam BankCardQuery query
    ) {
         DubboResponse<List<BindCardInfo>>d = refundOrderService.getBankCards(query);
        return rest(d);
    }

    @ApiOperation(value = "收款指令列表", notes = "收款指令列表")
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
    @RequestMapping(value = "/document-summary", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<DocumentSummaryResponse> refundReject(
            @BeanParam DocumentQuery query
    ) {
        DubboResponse<DocumentSummaryResponse> response = refundOrderService.getDocumentSummary(query);
        return rest(response);
    }

}
