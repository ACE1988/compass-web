package com.sdxd.api.app.lending.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.api.vo.lending.LoanParameterSetting;
import com.sdxd.api.vo.lending.PayParameterSetting;
import com.sdxd.apply.api.ApplySettingDubboService;
import com.sdxd.apply.request.ApplySettingRequest;
import com.sdxd.apply.response.ApplySettingInfo;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.loan.api.LoanSettingDubboService;
import com.sdxd.loan.enums.SettingVariable;
import com.sdxd.loan.request.LoanSettingRequest;
import com.sdxd.loan.response.LoanSettingInfo;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;

import java.util.ArrayList;
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
 * 17/05/116     刘节                 Created
 */
@Api(value = "Parameter-API", produces = "application/json")
@Controller
@RequestMapping(value = "/parameter", produces = "application/json")
public class ParameterSettingController {

    @Reference(version = "1.0.0")
    private LoanSettingDubboService loanSettingDubboService ;

    @Reference(version = "1.0.0")
    private ApplySettingDubboService applySettingDubboService;

    @ApiOperation(value = "放款参数设置", notes = "放款参数设置")
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
    @RequestMapping(value = "loan", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<String> loanParameterSetting(
            @Valid @BeanParam LoanParameterSetting query
    ) {
        LoanSettingRequest request = new LoanSettingRequest() ;
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setValue(query.getValue());
        request.setVariable(query.getKey());
        DubboResponse<String> response = loanSettingDubboService.setVariable(request);
        return rest(response);
    }

    @ApiOperation(value = "查询放款参数列表", notes = "查询放款参数列表")
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
    @RequestMapping(value = "query-loan", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<LoanSettingInfo>> getLoanAllVariable(
    ) {
        DubboResponse<List<LoanSettingInfo>> response = loanSettingDubboService.getAllVariable();
        List<LoanSettingInfo> list = response.getData();
        List<LoanSettingInfo> newList = new ArrayList<LoanSettingInfo>();
        for (LoanSettingInfo info:list){
            SettingVariable settingVariable = SettingVariable.getByVariable(info.getVariable());
            if(settingVariable != null){
                info.setVariable(settingVariable.name());
                newList.add(info);
            }

        }
        response.setData(newList);
        return rest(response);
    }

    @ApiOperation(value = "借款参数设置", notes = "借款参数设置")
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
    @RequestMapping(value = "pay", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<String> payParameterSetting(
            @Valid @BeanParam PayParameterSetting query
    ) {
        ApplySettingRequest request = new ApplySettingRequest() ;
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setValue(query.getValue());
        request.setVariable(query.getKey());
        DubboResponse<String> response = applySettingDubboService.setVariable(request);
        return rest(response);
    }

    @ApiOperation(value = "查询借款参数列表", notes = "查询借款参数列表")
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
    @RequestMapping(value = "query-pay", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<ApplySettingInfo>> getPayAllVariable(
    ) {
        DubboResponse<List<ApplySettingInfo>> response = applySettingDubboService.getAllVariable();
        List<ApplySettingInfo> list = response.getData();
        List<ApplySettingInfo> newList = new ArrayList<ApplySettingInfo>();
        for (ApplySettingInfo info:list){
            com.sdxd.apply.enums.SettingVariable settingVariable = com.sdxd.apply.enums.SettingVariable.getByVariable(info.getVariable());
            if(settingVariable != null){
                info.setVariable(settingVariable.name());
                newList.add(info);
            }
        }
        response.setData(newList);
        return rest(response);
    }
}
