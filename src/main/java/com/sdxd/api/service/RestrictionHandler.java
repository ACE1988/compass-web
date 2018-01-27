package com.sdxd.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sdxd.admin.dubbo.api.UserRoleService;
import com.sdxd.admin.dubbo.api.request.AdminIdRequest;
import com.sdxd.admin.dubbo.api.response.RolesResponse;
import com.sdxd.common.utils.BillNoUtils;
import com.sdxd.common.web.interceptor.PreHandler;
import com.sdxd.common.web.rest.RestContext;
import com.sdxd.common.web.security.Restrict;
import com.sdxd.common.web.security.Subject;
import com.sdxd.common.web.util.JsonUtil;
import com.sdxd.common.web.util.RequestUtil;
import com.sdxd.framework.dubbo.DubboResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.sdxd.common.web.util.MapUtil.$;
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
 * 17/1/22     melvin                 Created
 */
@Component
public class RestrictionHandler implements PreHandler {

    private static final Logger log = LoggerFactory.getLogger(RestrictionHandler.class);

    @Reference(version = "1.0.0")
    private UserRoleService userRoleService;

    @Override
    public boolean handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HandlerMethod.class.isInstance(handler)) {
            HandlerMethod handlerMethod = HandlerMethod.class.cast(handler);
            boolean matchedPackageBase = RestContext.isMatchPackageBase(handlerMethod.getBeanType());
            if (!matchedPackageBase) {
                return true;
            }

            Class<?> beanType = handlerMethod.getBeanType();
            boolean restrictInType = beanType.isAnnotationPresent(Restrict.class);

            Restrict restrict = handlerMethod.getMethodAnnotation(Restrict.class);
            if (!restrictInType && restrict == null) {
                return true;
            }

            Subject subject = (Subject) RequestUtil.getContextParam("subject", request, false);
            if (subject.isAccess()) {
                return true;
            }
            String adminId = subject.getValue();
            AdminIdRequest adminIdRequest = new AdminIdRequest();
            adminIdRequest.setRequestId(BillNoUtils.GenerateBillNo());
            adminIdRequest.setAdminId(adminId);
            DubboResponse<RolesResponse> dubboResponse = userRoleService.getRolesByUserId(adminIdRequest);
            RolesResponse rolesResponse = data(dubboResponse);
            String json = JsonUtil.toJson(rolesResponse);
            //noinspection unchecked
            Map<String, Object> map = JsonUtil.fromJson(json, Map.class);
            Object roles = map.get("adminRolesPos");
            Object permissions = map.get("adminPermissionsPos");

            Subject newSubject =
                    new Subject(adminId, false, $(
                            "permissions", permissions,
                            "roles", roles));
            request.setAttribute("subject", newSubject);
        }
        return true;
    }
}
