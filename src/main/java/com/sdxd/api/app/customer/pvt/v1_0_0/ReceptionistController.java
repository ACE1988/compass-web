package com.sdxd.api.app.customer.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.OperatorInfoDubboService;
import com.sdxd.admin.dubbo.api.request.AdminUserRequest;
import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.admin.dubbo.api.response.OutUserResponse;
import com.sdxd.api.service.ReceptionistService;
import com.sdxd.api.vo.customer.Receptionist;
import com.sdxd.api.vo.customer.ReceptionistAccount;
import com.sdxd.api.vo.customer.ReceptionistUpdate;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.customer.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/9     melvin                 Created
 */
@Api(value = "Privacy-API", produces = "application/json")
@Controller
@RequestMapping(value = "/receptionist", produces = "application/json")
public class ReceptionistController {

    @Reference(version = "1.0.0")
    private OperatorInfoDubboService operatorInfoDubboService;

    @Autowired
    private ReceptionistService receptionistService;

    @ApiOperation(value = "获取接待员信息", notes = "获取接待员信息")
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
    public RestResponse<Receptionist> getReceptionist(
            @ContextParam(value = "principal") String operatorId
    ) {
        AdminUserRequest request = new AdminUserRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(operatorId);
        DubboResponse<AdminUserResponse> response = operatorInfoDubboService.queryAdminUserById(request);
        try {
            OutUserResponse account = receptionistService.getReceptionist(operatorId);
            return rest(response, admin -> {
                Receptionist receptionist = new Receptionist();
                receptionist.setName(admin.getName());
                receptionist.setAccount(account);
                return receptionist;
            });
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "创建接待员信息", notes = "创建接待员信息")
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
    public RestResponse<Boolean> createReceptionist(
            @ContextParam(value = "principal") String operatorId,
            @RequestBody ReceptionistAccount account
    ) {
        try {
            Boolean result = receptionistService.createReceptionist(account.getId(), account.getName(), account.getPassword());
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "修改接待员信息", notes = "修改接待员信息")
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
    public RestResponse<Boolean> updateReceptionist(
            @ContextParam(value = "principal") String operatorId,
            @RequestBody ReceptionistUpdate update
    ) {
        try {
            Boolean result = receptionistService.updateReceptionist(
                    update.getId(), update.getName(), update.getPassword(), update.getNewPassword());
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
