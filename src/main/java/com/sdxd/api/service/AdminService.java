package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.OperatorInfoDubboService;
import com.sdxd.admin.dubbo.api.UserCenterDoubboService;
import com.sdxd.admin.dubbo.api.request.*;
import com.sdxd.admin.dubbo.api.response.AdminUserResponse;
import com.sdxd.admin.dubbo.api.response.RolePermissionResponse;
import com.sdxd.api.vo.admin.Admin;
import com.sdxd.api.vo.admin.AdminRoleUpdate;
import com.sdxd.api.vo.admin.QueryAdminList;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dto.PaginationSupport;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.sdxd.api.vo.AdminErrors.ADMIN_NOT_FOUND;
import static com.sdxd.common.web.util.ResponseUtil.data;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.service
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/12/5     melvin                 Created
 */
@Service
public class AdminService {

    @Reference(version = "1.0.0")
    private OperatorInfoDubboService operatorInfoDubboService;

    @Reference(version ="1.0.0")
    private UserCenterDoubboService userCenterDoubboService;

    public AdminUserResponse getAdminById(String adminId) throws ProcessBizException {
        AdminUserRequest request = new AdminUserRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(adminId);
        DubboResponse<AdminUserResponse> response = operatorInfoDubboService.queryAdminUserById(request);
        AdminUserResponse admin = data(response);
        if (admin == null) {
            throw new ProcessBizException(ADMIN_NOT_FOUND);
        }
        return admin;
    }

    public PaginationSupport<Admin> getAdminList(QueryAdminList query,String account) throws ProcessBizException {
        UserRoleRequest request = new UserRoleRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setRoleName(query.getRole());
        request.setId(query.getAccount());
        request.setApproveStatus(query.getStatus());
        request.setCurrentPage(query.getCurrentPage());
        request.setPageSize(query.getPageSize());
        request.setName(query.getName());
        request.setPhone(query.getPhone());
        request.setAccount(account);
        DubboResponse<PaginationSupport<AdminUserResponse>> response = userCenterDoubboService.selectUser(request);
        return data(response, value -> {
            List<AdminUserResponse> list = value == null ? Collections.EMPTY_LIST : value.getList();
            List<Admin> admins = list.stream().map(Admin::new).collect(Collectors.toList());
            int totalCount = value == null ? 0 : value.getTotalCount();
            int pageSize = value == null ? 0 : value.getPageSize();
            int pageNo = value == null ? 0 : value.getStartIndex();
            return new PaginationSupport<>(admins, totalCount, pageSize, pageNo);
        });
    }

    public Integer countAdminList(QueryAdminList query,String account) throws ProcessBizException {
        UserRoleRequest request = new UserRoleRequest();

        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setRoleName(query.getRole());
        request.setId(query.getAccount());
        request.setApproveStatus(query.getStatus());
        request.setCurrentPage(query.getCurrentPage());
        request.setPageSize(query.getPageSize());
        request.setAccount(account);

        DubboResponse<Integer> response = userCenterDoubboService.count(request);
        return data(response);
    }

    public RolePermissionResponse getRoles(String role, String system) throws ProcessBizException {
        UserCenterRoleRequest request = new UserCenterRoleRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setRoleName(role);
        request.setSystemName(system);
        DubboResponse<RolePermissionResponse> response = userCenterDoubboService.allPermission(request);
        return data(response);
    }

    public RolePermissionResponse getRolesByAdminId(String adminId) throws ProcessBizException {
        RolePermissionRequest request = new RolePermissionRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(adminId);
        DubboResponse<RolePermissionResponse> response = userCenterDoubboService.rolePermission(request);
        return data(response);
    }

    public Boolean updateRoles(String adminId, List<AdminRoleUpdate> updates) throws ProcessBizException {
        UpdateRolePermissionRequest request = new UpdateRolePermissionRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setAccount(adminId);
        if (updates != null) {
            List<UpdateRolePermissionPojo> values =
                updates.stream().map(value -> {
                    UpdateRolePermissionPojo update = new UpdateRolePermissionPojo();
                    update.setRoleId(value.getRoleId());
                    update.setRoleStatus(value.getOperation());
                    return update;
                }).collect(Collectors.toList());
            request.setRoleIds(values);
        }
        DubboResponse<Boolean> response = userCenterDoubboService.updateRolePermission(request);
        return data(response);
    }

    public Boolean approveRolesUpdate(
            String adminId, Integer approveStatus,
            String comment, String operatorId)
            throws ProcessBizException {
        ApproveRequest request = new ApproveRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(adminId);
        request.setApprove(approveStatus);
        request.setMsg(comment);
        request.setAccount(operatorId);
        DubboResponse<Boolean> response = userCenterDoubboService.approve(request);
        return data(response);
    }
}
