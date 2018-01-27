package com.sdxd.api.app.credit.pvt.v1_0_0;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.UserRoleService;
import com.sdxd.admin.dubbo.api.request.RoleRequest;
import com.sdxd.admin.dubbo.api.response.AdminUserRoleResponse;
import com.sdxd.api.vo.Roles;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.BeanParam;

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
@Api(value = "Approver-API", produces = "application/json")
@Controller
@RequestMapping(value = "/approvers", produces = "application/json")
public class ApproverController {

    @Reference(version = "1.0.0")
    private UserRoleService userRoleService;

    @ApiOperation(value = "获取信审员列表", notes = "获取信审员列表")
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
    public RestResponse<AdminUserRoleResponse> approvers(
            @BeanParam Roles roles) {
        RoleRequest request = new RoleRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setRoleIds(roles.getIds());
        DubboResponse<AdminUserRoleResponse> response = userRoleService.getAllUserByRole(request);
        return rest(response);
    }
}
