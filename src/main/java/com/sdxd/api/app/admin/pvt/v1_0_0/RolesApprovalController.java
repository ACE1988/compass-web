package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.sdxd.api.service.AdminService;
import com.sdxd.api.vo.admin.AdminRoleApproval;
import com.sdxd.common.web.rest.ContextParam;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
 * 17/4/13     melvin                 Created
 */
@Api(value = "Admin-API", produces = "application/json")
@Controller
@RequestMapping(value = "/admin", produces = "application/json")
public class RolesApprovalController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "审核用户角色修改", notes = "审核用户角色修改")
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
    @RequestMapping(value = "/{id}/roles-changing", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Boolean> approveRolesChanging(
            @ApiParam(value = "后台用户ID") @PathVariable(value = "id") String account,
            @RequestBody AdminRoleApproval approval,
            @ContextParam(value = "principal") String operatorId
            ) {
        try {
            Boolean result = adminService.approveRolesUpdate(
                    account, approval.getOperation(), approval.getComment(), operatorId);
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }
}
