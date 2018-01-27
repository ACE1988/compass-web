package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.SystemVariableDubboService;
import com.sdxd.admin.dubbo.api.request.VariableCodeRequest;
import com.sdxd.api.service.PublicResourceService;
import com.sdxd.api.vo.credit.AccountCodeDetail;
import com.sdxd.api.vo.credit.AutoCreditSettings;
import com.sdxd.api.vo.credit.AutoStatus;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import java.util.logging.Logger;

import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * Created by lenovo on 2017/7/24.
 */
@Api(value = "Auto-Send-API", produces = "application/json")
@Controller
@RequestMapping(value = "/auto")
public class AutoController {

    @Reference(version = "1.0.0")
    private SystemVariableDubboService systemVariableDubboService;

    @Autowired
    private PublicResourceService publicResourceService;

    @ApiOperation(value = "系统自动发送", notes = "系统自动发送")
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
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<PaginationSupport<AccountCodeDetail>> send(@Valid @BeanParam AutoCreditSettings settings) {
        VariableCodeRequest variableCodeRequest0 = new VariableCodeRequest();
        variableCodeRequest0.setVariableCode(String.valueOf(settings.getCodes()));
        variableCodeRequest0.setVariableValue("AUTO_PASS_CODES");
        variableCodeRequest0.setRequestId(BillNoUtils.GenerateBillNo());
        systemVariableDubboService.updateValueByCodeStr(variableCodeRequest0);

        //注意这里的code和value的值由于借口，所以反传
        //添加smsCounbt状态
        VariableCodeRequest variableCodeRequest1 = new VariableCodeRequest();
        variableCodeRequest1.setVariableCode(String.valueOf(settings.getSmsCount()));
        variableCodeRequest1.setVariableValue("AUTO_PASS_LIMIT");
        variableCodeRequest1.setRequestId(BillNoUtils.GenerateBillNo());
        systemVariableDubboService.updateValueByCodeStr(variableCodeRequest1);

        //添加开关状态
        VariableCodeRequest variableCodeRequest2 = new VariableCodeRequest();
        variableCodeRequest2.setVariableCode(String.valueOf(settings.getAuto()));
        variableCodeRequest2.setVariableValue("SHUTDOWN_STATUS");
        variableCodeRequest2.setRequestId(BillNoUtils.GenerateBillNo());
        systemVariableDubboService.updateValueByCodeStr(variableCodeRequest2);
        try {
            String shutdownStatus = publicResourceService.getVariable("SHUTDOWN_STATUS");
            Logger.getLogger(shutdownStatus);
        } catch (ProcessBizException e) {
            e.printStackTrace();
        }
        //添加手机状态
        VariableCodeRequest variableCodeRequest3 = new VariableCodeRequest();
        variableCodeRequest3.setVariableCode(settings.getPhone());
        variableCodeRequest3.setVariableValue("APPROVER_PHONE_NOTIFY");
        variableCodeRequest3.setRequestId(BillNoUtils.GenerateBillNo());
        systemVariableDubboService.updateValueByCodeStr(variableCodeRequest3);
        return ok();
    }


    @ApiOperation(value = "系统自动获取状态", notes = "系统自动获取状态")
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
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<AutoStatus> getStatus() {
        try {
            //添加状态
            String autoPassCodes = publicResourceService.getVariable("AUTO_PASS_CODES");
            String autoPassLimit = publicResourceService.getVariable("AUTO_PASS_LIMIT");
            String shutdownStatus = publicResourceService.getVariable("SHUTDOWN_STATUS");
            String approverPhoneotify = publicResourceService.getVariable("APPROVER_PHONE_NOTIFY");
            AutoStatus autoStatus = new AutoStatus();
            //注入
            autoStatus.setAutoPassCodes(autoPassCodes);
            autoStatus.setApproverPhoneotify(approverPhoneotify);
            autoStatus.setAutoPassLimit(autoPassLimit);
            autoStatus.setShutdownStatus(shutdownStatus);
            return ok(autoStatus);
        } catch (ProcessBizException e) {
            return e.toResult();
        }


    }


}
