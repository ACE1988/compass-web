package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.balance.api.BalanceSettingDubboService;
import com.sdxd.balance.enums.SettingVariable;
import com.sdxd.balance.request.withdraw.BalanceSettingRequest;
import com.sdxd.balance.request.withdraw.GetBalanceSettingRequest;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.sdxd.common.web.util.ResponseUtil.rest;

@Api(value="Balance-API", produces = "application/json")
@Controller
@RequestMapping(value = "/balance", produces = "application/json")
public class BalanceController {

    @Reference(version = "1.0.0")
    private BalanceSettingDubboService balanceSettingDubboService;

    @ApiOperation(value = "修改最小提现金额", notes = "修改最小提现金额")
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
    @RequestMapping(value = "/updateWithdrawAmount", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse updateWithdrawAmount(
            @RequestParam String amount
    ){
        BalanceSettingRequest request = new BalanceSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariable(SettingVariable.WITHDRAW_AMOUNT);
        request.setValue(amount);
        DubboResponse<String> dubboResponse = balanceSettingDubboService.setVariable(request);
        return rest(dubboResponse);
    }

    @ApiOperation(value = "查询最小提现金额", notes = "查询最小提现金额")
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
    @RequestMapping(value = "/getWithdrawAmount", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse getWithdrawAmount(){
        GetBalanceSettingRequest request = new GetBalanceSettingRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setVariable(SettingVariable.WITHDRAW_AMOUNT);
        DubboResponse<String> dubboResponse = balanceSettingDubboService.getVariable(request);
        return rest(dubboResponse);
    }


}
