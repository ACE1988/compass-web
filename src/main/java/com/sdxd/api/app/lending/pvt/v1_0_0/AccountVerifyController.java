package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.service.AccountVerifyService;
import com.sdxd.api.vo.lending.AccountVerificationQuery;
import com.sdxd.api.vo.lending.PaymentExcDetailQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.data.dubbo.api.pay.PayReconDailyDubboService;
import com.sdxd.data.dubbo.api.request.PayReconDailyRequest;
import com.sdxd.data.dubbo.api.response.pojo.PayDZExcDetailBo;
import com.sdxd.data.dubbo.api.response.pojo.PayReconDailyBo;
import com.sdxd.data.dubbo.api.response.pojo.ReportRepaymentDailyBo;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;
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
 * 17/3/16     melvin                 Created
 */
@Api(value = "Account-Verification-API", produces = "application/json")
@Controller
@RequestMapping(value = "/account-verification", produces = "application/json")
public class AccountVerifyController {

    @Reference(version = "1.0.0")
    private PayReconDailyDubboService payReconDailyDubboService;

    @Autowired
    private AccountVerifyService accouuntVerifyService ;

    @ApiOperation(value = "对账汇总列表", notes = "对账汇总列表")
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
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<PayReconDailyBo>> getRulesHit(
            @Valid @BeanParam AccountVerificationQuery query) {
        PayReconDailyRequest request = new PayReconDailyRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setStartTime(query.getStartTime());
        request.setEndTime(query.getEndTime());
        DubboResponse<List<PayReconDailyBo>> response = payReconDailyDubboService.query(request);
        return rest(response);
    }

    @ApiOperation(value = "盛付通对账结果异常明细", notes = "盛付通对账结果异常明细")
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
    @RequestMapping(value = "/exp-detail", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<PaginationSupport<PayDZExcDetailBo>> getPayExcDetail(
            @BeanParam PaymentExcDetailQuery query
    ) {
        try {
            PaginationSupport<PayDZExcDetailBo> page =
                    forValue(new PaginationSupport<PayDZExcDetailBo>(null, 0)).
                            parallel(() -> accouuntVerifyService.query(query), (p1, p2) -> {
                                p1.setList(p2 == null ? null : p2.getList());
                                p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                                p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                            }).
                            parallel(() -> accouuntVerifyService.count(query), PaginationSupport::setTotalCount).
                            start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
