package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sdxd.admin.dubbo.api.response.RolePermissionResponse;
import com.sdxd.api.service.AdminService;
import com.sdxd.api.vo.PK;
import com.sdxd.api.vo.admin.AdminRole;
import com.sdxd.api.vo.admin.AdminRoleUpdate;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rx.Observable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.sdxd.common.web.util.Throwables.propagate;
import static com.sdxd.common.web.util.Throwables.toResponse;
import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * Created by Administrator on 2017/4/11.
 */
@Api(value = "Admin-Roles-API", produces = "application/json")
@Controller
@RequestMapping(value = "/admin", produces = "application/json")
public class RoleAdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "获取所有角色列表", notes = "获取所有角色列表")
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
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<AdminRole>> getAllRoles(
            @ApiParam(value = "角色") @RequestParam(value = "role", required = false) String role,
            @ApiParam(value = "系统") @RequestParam(value = "system", required = false) String system) {
        return getRoles(() -> propagate(() -> adminService.getRoles(role, system)));
    }

    @ApiOperation(value = "获取用户角色列表", notes = "获取用户角色列表")
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
    @RequestMapping(value = "/{id}/roles", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<List<AdminRole>> getUserRole(
            @ApiParam(value = "用户ID", required = true) @PathVariable(value = "id") String adminId) {
        return getRoles(() -> propagate(() -> adminService.getRolesByAdminId(adminId)));
    }

    @ApiOperation(value = "修改用户角色", notes = "修改用户角色")
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
    @RequestMapping(value = "/{id}/roles", method = RequestMethod.PATCH)
    @ResponseBody
    public RestResponse<Boolean> updateUserRole(
            @ApiParam(value = "用户ID", required = true) @PathVariable(value = "id") String adminId,
            @RequestBody PK<String> updates
    ) {
        try {
            RolePermissionResponse response = adminService.getRolesByAdminId(adminId);
            Map<String, AdminRoleUpdate> map = Maps.newHashMap();
            if (response != null && response.getPer() != null) {
                response.getPer().forEach(value -> {
                    AdminRoleUpdate update = map.get(value.getRoleId());
                    if (update == null) {
                        update = new AdminRoleUpdate();
                        update.setRoleId(value.getRoleId());
                        update.setOperation(-1);
                        map.put(value.getRoleId(), update);
                    }
                });
            }
            if (updates != null && updates.getIds() != null) {
                updates.getIds().forEach(value -> {
                    AdminRoleUpdate update = map.get(value);
                    if (update == null) {
                        update = new AdminRoleUpdate();
                        update.setRoleId(value);
                        update.setOperation(1);
                        map.put(value, update);
                    } else {
                        update.setOperation(0);
                    }
                });
            }
            List<AdminRoleUpdate> roles = Lists.newArrayList(map.values());
            Boolean result = adminService.updateRoles(adminId, roles);
            return ok(result);
        } catch (ProcessBizException e) {
            return e.toResult();
        }
    }

    private RestResponse<List<AdminRole>> getRoles(Supplier<RolePermissionResponse> supplier) {
        try {
            RolePermissionResponse result = supplier.get();
            if (result == null || result.getPer() == null) {
                return ok();
            }

            Map<String, AdminRole> roles = Maps.newHashMap();
            return ok(
                    Observable.from(result.getPer()).
                            reduce(roles, (roleMap, rolePermission) -> {
                                AdminRole role = roleMap.get(rolePermission.getRoleId());
                                if (role == null) {
                                    role = new AdminRole(rolePermission);
                                    roleMap.put(rolePermission.getRoleId(), role);
                                } else {
                                    role.mergePermission(rolePermission);
                                }
                                return roleMap;
                            }).
                            map(Map::values).
                            map(Lists::newArrayList).
                            flatMap(Observable::from).
                            toSortedList((r1, r2) -> r1.getId().compareTo(r2.getId())).
                            toBlocking().first());
        } catch (RuntimeException e) {
            return toResponse(e);
        }
    }
}
