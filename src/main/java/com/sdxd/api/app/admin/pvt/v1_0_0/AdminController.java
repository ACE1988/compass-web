package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.AdminLoginDubboService;
import com.sdxd.admin.dubbo.api.UserCenterDoubboService;
import com.sdxd.admin.dubbo.api.request.AdminUserRequest;
import com.sdxd.admin.dubbo.api.request.ResetPassWordRequest;
import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.admin.dubbo.api.response.OffResponse;
import com.sdxd.admin.dubbo.api.response.ResetPassWordResponse;
import com.sdxd.api.service.AdminService;
import com.sdxd.api.service.ProfileService;
import com.sdxd.api.vo.PasswordReset;
import com.sdxd.api.vo.admin.*;
import com.sdxd.approve.dubbo.api.PagingQuery;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import com.sdxd.message.dubbo.api.PhoneDubboService;
import com.sdxd.message.dubbo.request.phoneblack.BlackPhoneRequest;
import com.sdxd.user.api.UserService;
import com.sdxd.user.api.response.UserInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;

import static com.sdxd.api.vo.CreditErrors.LENDER_NOT_FOUND;
import static com.sdxd.api.vo.CreditErrors.NO_PARAMETERS;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.util.ResponseUtil.data;
import static com.sdxd.common.web.util.dubbo.DubboLoading.forValue;
import static com.sdxd.common.web.vo.RestResponse.fail;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admin.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/1/9     melvin                 Created
 */
@Api(value = "Adim-API", produces = "application/json")
@Controller
@RequestMapping(value = "/admin", produces = "application/json")
public class AdminController {

    @Reference(version = "1.0.0")
    private AdminLoginDubboService adminLoginDubboService;

    @Reference(version = "1.0.0")
    private UserCenterDoubboService userCenterDoubboService;

    @Reference(version = "1.0.0")
    private PagingQuery pagingQuery;

    @Reference(version = "1.0.0")
    private PhoneDubboService phoneDubboService;

    @Reference(version = "1.0.0")
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "修改管理员密码", notes = "修改管理员密码")
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
    @RequestMapping(value = "/password", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse updatePassword(
            @ContextParam(value = "principal") String userId,
            @Valid @RequestBody PasswordReset reset) {
        ResetPassWordRequest request = new ResetPassWordRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(userId);
        request.setPassword(reset.getOriginPassword());
        request.setNewPassword(reset.getNewPassword());
        DubboResponse<ResetPassWordResponse> response = adminLoginDubboService.resetPassword(request);
        return rest(response);
    }

    @ApiOperation(value = "创建用户", notes = "创建用户")
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
    public RestResponse createAdmin(
            @Valid @RequestBody CreateAdminRequest create) {
        AdminUserRequest request = new AdminUserRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(create.getAccount());
        request.setName(create.getName());
        request.setPhone(create.getPhone());
        request.setPassword(create.getPassword());
        request.setWhiteListUser(create.getWhiteListUser());
        userCenterDoubboService.newUser(request);
        return ok();
    }

    @ApiOperation(value = "编辑用户", notes = "编辑用户")
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
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse editAdmin(
            @Valid @RequestBody EditAdminRequest edit) {
        AdminUserRequest request = new AdminUserRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(edit.getAccount());
        request.setName(edit.getName());
        request.setPhone(edit.getPhone());
        request.setWhiteListUser(edit.getWhiteListUser());
        userCenterDoubboService.updateUser(request);
        return ok();
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
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
    @RequestMapping(value = "/{account}", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Admin> queryAdmin(
            @PathVariable("account") String account) {
        try {
            AdminUserResponse response = adminService.getAdminById(account);
            return ok(new Admin(response));
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "启用/禁用后台用户", notes = "启用/禁用后台用户")
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
    @RequestMapping(value = "/switch", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<UseStatus> switchAdmin(
            @Valid @RequestBody SwitchAdmin switchAdmin) {

        AdminUserRequest request = new AdminUserRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(switchAdmin.getAccount());

        DubboResponse<OffResponse> response = userCenterDoubboService.off(request);

        return rest(response, UseStatus::new);
    }

    @ApiOperation(value = "重置后台用户密码", notes = "重置后台用户密码")
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
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<PasswordStatus> resetPassword(
            @Valid @RequestBody ResetPassword reset) {
        AdminUserRequest request = new AdminUserRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(reset.getAccount());
        request.setPassword(reset.getPassword());

        DubboResponse<ResetPassWordResponse> response = userCenterDoubboService.resetPassword(request);

        return rest(response, PasswordStatus::new);
    }

    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
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
    public RestResponse<PaginationSupport<Admin>> search(@ContextParam(value = "principal") String account,
                                                         @Valid @BeanParam QueryAdminList query) {
        try {
            PaginationSupport<Admin> page = forValue(new PaginationSupport<Admin>(null, 0)).
                    parallel(() -> adminService.getAdminList(query, account), (p1, p2) -> {
                        p1.setList(p2 == null ? null : p2.getList());
                        p1.setCurrentIndex(p2 == null ? 0 : p2.getCurrentIndex());
                        p1.setPageSize(p2 == null ? 0 : p2.getPageSize());
                    }).
                    parallel(() -> adminService.countAdminList(query, account), PaginationSupport::setTotalCount).
                    start();
            return ok(page);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    @ApiOperation(value = "销户", notes = "销户")
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
    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse unregisterUser(
            @ApiParam(value = "用户ID")
            @RequestParam(value = "user_id", required = false) Long userId,
            @ApiParam(value = "手机号")
            @RequestParam(value = "phone", required = false) String phone
    ) {
        try {
            if (userId == null && StringUtils.isBlank(phone)) {
                return fail(NO_PARAMETERS);
            }
            if (userId == null && StringUtils.isNotBlank(phone)) {
                userId = profileService.getUserId(phone);
            }
            DubboResponse<Boolean> unregisterResponse = userService.unRegister(userId);
            Boolean result = data(unregisterResponse);
            if (result == null || !result) {
                return ok(false);
            }
            if (phone == null) {
                UserInfo userInfo = profileService.getUserInfo(userId);
                if (userInfo == null) return fail(LENDER_NOT_FOUND);

            }
            BlackPhoneRequest request = new BlackPhoneRequest();
            request.setRequestId(BillNoUtils.GenerateBillNo());
            request.setPhone(phone);
            DubboResponse<Boolean> response = phoneDubboService.addPhoneBlack(request);
            return rest(response);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
