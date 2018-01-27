package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.AdminLoginDubboService;
import com.sdxd.admin.dubbo.api.request.LoginRequest;
import com.sdxd.admin.dubbo.api.response.LoginResponse;
import com.sdxd.api.vo.SignIn;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.auth.Authentication;
import com.sdxd.common.web.auth.IAuthenticationApi;
import com.sdxd.common.web.auth.ICredential;
import com.sdxd.common.web.security.Subject;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import com.sdxd.framework.dubbo.DubboResponse;
import org.springframework.stereotype.Component;

import static com.sdxd.common.web.util.MapUtil.$;
import static com.sdxd.common.web.util.ResponseUtil.rest;
import static com.sdxd.common.web.vo.ErrorCode.AuthenticationError.UNKNOWN_CREDENTIAL;

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
 * 17/5/27     melvin                 Created
 */
@Authentication(path = "/api/pub/1.0.0/admin/token", credentialType = SignIn.class)
@Component
public class SignInApi implements IAuthenticationApi {

    @Reference(version = "1.0.0")
    private AdminLoginDubboService adminLoginDubboService;

    @Override
    public RestResponse<Subject> doAuthenticate(ICredential credential) throws ProcessBizException {
        if (!SignIn.class.isInstance(credential)) {
            throw new ProcessBizException(UNKNOWN_CREDENTIAL);
        }

        SignIn signIn = SignIn.class.cast(credential);
        LoginRequest request = new LoginRequest();
        request.setRequestId(BillNoUtils.GenerateBillNo());
        request.setId(signIn.getUser());
        request.setPassword(signIn.getPassword());
        DubboResponse<LoginResponse> response = adminLoginDubboService.submitLogin(request);
        return rest(response,
                result -> new Subject(result.getId(), false,
                        $("userName", result.getName(),
                                "permissions", result.getAdminPermissionsPos(),
                                "roles", result.getAdminRolesPos())));
    }
}
