package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.pay.api.CapitalProviderDubboService;
import com.sdxd.pay.api.P2PMerchantInfoDubboService;
import com.sdxd.pay.response.CapitalProviderInfo;
import com.sdxd.pay.response.MerchantInfoQueryResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.sdxd.common.web.util.ResponseUtil.rest;

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
 * 16/12/6     melvin                 Created
 */
@Api(value = "Capital-Provider-API", produces = "application/json")
@Controller
@RequestMapping(value = "/capital-provider", produces = "application/json")
public class CapitalProviderController {

    @Reference(version = "1.0.0")
    private CapitalProviderDubboService capitalProviderDubboService;

    @Reference(version = "1.0.0")
    private P2PMerchantInfoDubboService p2PMerchantInfoDubboService; //getAllMerchantInfo

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
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<CapitalProviderInfo>> query() {
        DubboResponse<List<CapitalProviderInfo>> response = capitalProviderDubboService.selectProvidersInfo();
        return rest(response);
    }

    @ApiOperation(value = "获取盛付通帐户列表", notes = "获取盛付通帐户列表")
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
    @RequestMapping(value = "/sheng-pay-accounts", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<MerchantInfoQueryResponse>> queryAccounts() {
        DubboResponse<List<MerchantInfoQueryResponse>> info = p2PMerchantInfoDubboService.getAllMerchantInfo();
        return rest(info);
    }
}
