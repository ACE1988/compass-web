package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.AdminOutUserDubboService;
import com.sdxd.admin.dubbo.api.constants.OutSystemName;
import com.sdxd.admin.dubbo.api.request.OutUserRequest;
import com.sdxd.admin.dubbo.api.request.OutUserUpdateRequest;
import com.sdxd.admin.dubbo.api.request.QureyOutUserRequest;
import com.sdxd.admin.dubbo.api.response.OutUserResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Service;

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
 * 17/3/14     melvin                 Created
 */
@Service
public class ReceptionistService {

    @Reference(version = "1.0.0")
    private AdminOutUserDubboService adminOutUserDubboService;

    public OutUserResponse getReceptionist(String operatorId) throws ProcessBizException {
        QureyOutUserRequest request = new QureyOutUserRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSysId(operatorId);
        request.setOutSystemName(OutSystemName.YUN_NIU.name());
        DubboResponse<OutUserResponse> response = adminOutUserDubboService.queryOutUser(request);
        return data(response);
    }

    public Boolean createReceptionist(String operatorId, String receptionist, String password) throws ProcessBizException {
        OutUserRequest request = new OutUserRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSysId(operatorId);
        request.setOutSystemName(OutSystemName.YUN_NIU.name());
        request.setOutName(receptionist);
        request.setOutPassword(password);
        DubboResponse<Boolean> response = adminOutUserDubboService.saveOutUser(request);
        return data(response);
    }

    public Boolean updateReceptionist(
            String operatorId, String receptionist, String originPassword, String newPassword) throws ProcessBizException {
        OutUserUpdateRequest request = new OutUserUpdateRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setSysId(operatorId);
        request.setOutSystemName(OutSystemName.YUN_NIU.name());
        request.setOutName(receptionist);
        request.setOutOldPassword(originPassword);
        request.setOutNewPassword(newPassword);
        DubboResponse<Boolean> response = adminOutUserDubboService.updateOutUser(request);
        return data(response);
    }
}
