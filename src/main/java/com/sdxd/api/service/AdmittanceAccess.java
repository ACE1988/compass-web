package com.sdxd.api.service;

import com.google.common.collect.Sets;
import com.sdxd.common.web.security.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.sdxd.common.web.trace.HttpTracer.DEBUG;
import static com.sdxd.common.web.util.MapUtil.$;

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
 * 17/1/19     melvin                 Created
 */
public class AdmittanceAccess extends DomainAccess {

    private static final Logger log = LoggerFactory.getLogger(AdmittanceAccess.class);

    private static Map<String, Set<Integer>> PERMISSIONS = $(
            "XS_APPLY_LIST", Sets.newHashSet(0, 1, 2, 3, 4, 17, 18),
            "XS_FIRST_LIST", Sets.newHashSet(1, 4, 17),
            "XS_FINAL_LIST", Sets.newHashSet(2, 3),
            "XS_COMPLETE_LIST", Sets.newHashSet(18, 19));

    private static Set<String> RESTRICT_IN_ASSIGNEE_ROLES = Sets.newHashSet("XS_MEMBER");
    private static Set<String> NOT_RESTRICT_IN_ASSIGNEE_ROLES = Sets.newHashSet("XS_MANAGER", "XS_LEADER");

    public static AdmittanceAccess of(Subject subject) {
        return new AdmittanceAccess(subject);
    }

    private AdmittanceAccess(Subject subject) {
        super(subject);
    }

    @Override
    Set<String> getNotRestrictRoles() {
        return NOT_RESTRICT_IN_ASSIGNEE_ROLES;
    }

    @Override
    Set<String> getRestrictRoles() {
        return RESTRICT_IN_ASSIGNEE_ROLES;
    }

    public boolean authorized(List<Integer> status) {
        if (status == null || status.size() == 0) {
            return false;
        }
        Set<String> permissions = Sets.newHashSet(getPermissionIds());
        Set<Integer> current = Sets.newHashSet(status);
        int count =
            Observable.from(PERMISSIONS.entrySet()).
                    filter(entry -> permissions.contains(entry.getKey())).
                    filter(entry -> entry.getValue().containsAll(current)).
                    count().toBlocking().first();
        return count > 0;
    }

    public boolean isGroupingRole(List<Integer> status) {
        if (status == null || status.size() == 0) {
            return false;
        }
        String groupingRestrictList = "XS_FINAL_LIST";
        Set<Integer> statusSet = PERMISSIONS.get(groupingRestrictList);
        DEBUG(log, "Final list status set: {}, checking status: {}", statusSet, status);
        if (statusSet == null || !statusSet.containsAll(status)) {
            return false;
        }
        String groupingRestrictRole = "XS_LEADER";
        return justRole(groupingRestrictRole);
    }

    public boolean isMember() {
        return getRoleIds() != null && getRoleIds().contains("XS_MEMBER");
    }

    private boolean justRole(String roleId) {
        List<String> roleIds = getRoleIds();
        DEBUG(log, "Current admin roles: {}, checking role: {}", roleIds, roleId);
        return roleIds != null && roleIds.contains(roleId) && !roleIds.contains("XS_MANAGER");
    }
}
