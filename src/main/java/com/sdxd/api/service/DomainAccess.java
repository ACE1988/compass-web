package com.sdxd.api.service;

import com.google.common.collect.Lists;
import com.sdxd.common.web.security.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

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
 * 17/3/9     melvin                 Created
 */
public abstract class DomainAccess {

    private static final Logger log = LoggerFactory.getLogger(DomainAccess.class);

    private Subject subject;
    private List<String> permissionIds;
    private List<String> roleIds;

    DomainAccess(Subject subject) {
        this.subject = subject;
        Map<String, Object> params = this.subject.getParams();
        List<Map<String, Object>> permissions = getPermissions(params);
        List<Map<String, Object>> roles = getRoles(params);
        permissionIds = Observable.from(permissions).
                map(permission -> permission.get("permissionId")).
                map(String::valueOf).
                toList().toBlocking().first();
        roleIds = Observable.from(roles).
                map(role -> role.get("rolesId")).
                map(String::valueOf).
                toList().toBlocking().first();
        log.debug("Try access admittance user {} roles: {}", subject.getValue(), roleIds);
    }

    public void restrict(Consumer<String> consumer) {
        int count = Observable.from(roleIds).
                filter(roleId -> getNotRestrictRoles().contains(roleId)).
                count().toBlocking().first();
        log.debug("Not restrict list match user {} roles count is {}", subject.getValue(), count);
        if (count > 0) {
            return;
        }
        boolean restrict = roleIds.containsAll(getRestrictRoles());
        log.debug("Restrict list match user {} roles result is {}", subject.getValue(), restrict);
        if (restrict) {
            consumer.accept(subject.getValue());
        }
    }

    List<String> getRoleIds() {
        return roleIds;
    }

    List<String> getPermissionIds() {
        return permissionIds;
    }

    abstract Set<String> getNotRestrictRoles();

    abstract Set<String> getRestrictRoles();

    private List<Map<String, Object>> getPermissions(Map<String, Object> params) {
        Object value = params.get("permissions");
        //noinspection unchecked
        return value == null ? Lists.newArrayList() : (List<Map<String, Object>>) value;
    }

    private List<Map<String, Object>> getRoles(Map<String, Object> params) {
        Object value = params.get("roles");
        //noinspection unchecked
        return value == null ? Lists.newArrayList() : (List<Map<String, Object>>) value;
    }
}
