package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.service.InstructionService;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.vo.lending.DeductStatus;
import com.sdxd.api.vo.lending.PaymentInstructLogQuery;
import com.sdxd.api.vo.lending.PaymentProxyQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.response.pojo.PayDfCommandBo;
import com.sdxd.data.dubbo.api.response.pojo.PayDkCommandBo;
import com.sdxd.data.dubbo.api.response.pojo.PayInstructLogBo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.pay.api.PaymentDubboService;
import com.sdxd.pay.request.ManualChangeDkStatusRequest;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.BeanParam;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.Throwables.toResponse;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
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
 * 17/3/17     melvin                 Created
 */
@Api(value = "Instruction-API", produces = "application/json")
@Controller
@RequestMapping(value = "/instruction", produces = "application/json")
public class InstructionController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private InstructionService instructionService;

    @Reference(version = "1.0.0")
    private PaymentDubboService paymentDubboService;

    @ApiOperation(value = "代付指令查询", notes = "代付指令查询")
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
    @RequestMapping(value = "/payment-proxy", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<PayDfCommandBo>> findRepaymentProxyInstruction(
            @BeanParam PaymentProxyQuery query
    ) {
        try {
            Long userId = null;
            if (StringUtils.isNotBlank(query.getCellphone())) {
                userId = profileService.getUserId(query.getCellphone());
                query.setUserId(userId);
            }
            PaginationSupport<PayDfCommandBo> page =
                    forValue(new PaginationSupport<PayDfCommandBo>(null, 0)).
                            parallel(() -> instructionService.findPaymentProxyResults(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> instructionService.countPaymentProxyResults(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "代扣指令查询", notes = "代扣指令查询")
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
    @RequestMapping(value = "/deduct-proxy", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<PayDkCommandBo>> findDeductProxyInstruction(
            @BeanParam PaymentProxyQuery query
    ) {
        try {
            PaginationSupport<PayDkCommandBo> page =
                    forValue(new PaginationSupport<PayDkCommandBo>(null, 0)).
                            parallel(() -> instructionService.findDeductProxyResults(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> instructionService.countDeductProxyResults(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "代扣指令状态修改", notes = "代扣指令状态修改")
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
    @RequestMapping(value = "/deduct-proxy/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse updateDeductProxyInstruction(
            @ApiParam(value = "还款单ID", required = true) @PathVariable("id") String id,
            @BeanParam DeductStatus status
    ) {
        ManualChangeDkStatusRequest request = new ManualChangeDkStatusRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setBizCode(id);
        request.setChannelCode(status.getChannel());
        request.setDesc(status.getDescription());
        request.setSuccess(status.getSuccess());
        DubboResponse<String> response = paymentDubboService.manualChangeDkStatus(request);
        return rest(response);
    }

    @ApiOperation(value = "代扣指令日志查询", notes = "代扣指令日志查询")
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
    @RequestMapping(value = "/instruct-log", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<PayInstructLogBo>> paymentInstructLog(
            @BeanParam PaymentInstructLogQuery query
    ) {
        try {
            DubboResponse<List<PayInstructLogBo>> list = instructionService.instructLog(query);
            return rest(list);
        }catch (ProcessBizException e){
            return toResponse(e);
        }

    }
}
