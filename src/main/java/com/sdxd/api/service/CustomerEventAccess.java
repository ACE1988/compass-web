package com.sdxd.api.service;

import com.google.common.collect.Sets;
import com.sdxd.common.web.security.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

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
public class CustomerEventAccess extends DomainAccess {

    private static final Logger log = LoggerFactory.getLogger(CustomerEventAccess.class);

    private static Set<String> RESTRICT_ROLES = Sets.newHashSet("KF_SEAT");
    private static Set<String> NOT_RESTRICT_ROLES = Sets.newHashSet("KF_MANAGER");

    public static CustomerEventAccess of(Subject subject) {
        return new CustomerEventAccess(subject);
    }

    private CustomerEventAccess(Subject subject) {
        super(subject);
    }

    @Override
    Set<String> getNotRestrictRoles() {
        return NOT_RESTRICT_ROLES;
    }

    @Override
    Set<String> getRestrictRoles() {
        return RESTRICT_ROLES;
    }


}
