package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.credit.CreditLine;
import com.sdxd.api.vo.credit.CreditLineIncrement;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.creditline.api.CreditLineDubboService;
import com.sdxd.creditline.request.SingleUserIdRequest;
import com.sdxd.creditline.request.UpdateCreditTotalRequest;
import com.sdxd.creditline.response.CreditLineHistoryInfo;
import com.sdxd.creditline.response.CreditLineInfo;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.copy;
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
public class CreditLineController {

    @Reference(version = "1.0.0")
    private CreditLineDubboService creditLineDubboService;

    @ApiOperation(value = "获取用户额度", notes = "获取用户额度")
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
    public RestResponse<CreditLine> getCreditLine(
            @ApiParam(value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId
    ) {
        SingleUserIdRequest request = new SingleUserIdRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setUserId(userId);
        DubboResponse<CreditLineInfo> response = creditLineDubboService.getByUserId(request);
        return rest(response, creditLine -> copy(creditLine, new CreditLine()));
    }

    @ApiOperation(value = "获取用户额度历史", notes = "获取用户额度历史")
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
    @RequestMapping(value = "/activities", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<CreditLineHistoryInfo>> getActivities(
            @ApiParam(value = "用户ID", required = true) @RequestParam(value = "user_id") Long userId
    ) {
//        SingleUserIdRequest request = new SingleUserIdRequest();
//        request.setRequestId(BillNoUtils.GenerateBillNo());
//        request.setUserId(userId);
//        DubboResponse<List<CreditLineHistoryInfo>> response = creditLineDubboService.queryCreditLineChangeHistory(request);
//        return rest(response);
        return null;
    }

    @ApiOperation(value = "修改用户额度", notes = "修改用户额度")
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
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<String> updateCreditLine(
            @Valid @RequestBody CreditLineIncrement increment
    ) {
        UpdateCreditTotalRequest request = new UpdateCreditTotalRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAmount(increment.getIncrement());
        request.setUserId(increment.getUserId());
        DubboResponse<String> response = creditLineDubboService.setCreditTotal(request);
        return rest(response);
    }
}
