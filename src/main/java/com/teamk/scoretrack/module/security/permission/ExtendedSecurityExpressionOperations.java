package com.teamk.scoretrack.module.security.permission;

import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import com.teamk.scoretrack.module.security.acl.AclService;
import org.springframework.security.access.expression.SecurityExpressionOperations;

public interface ExtendedSecurityExpressionOperations extends SecurityExpressionOperations {
    AclService getAclService();

    default boolean isSupport() {
        return getAclService().checkAcl(UserGroup.SUPPORT);
    }

    default boolean isClient() {
        return getAclService().checkAcl(UserGroup.CLIENT);
    }

    default boolean hasUserGroup(UserGroup userGroup) {
        return getAclService().checkAcl(userGroup);
    }

    default boolean hasApiAccess(int apiCode) {
        return getAclService().checkApiAcl(apiCode);
    }
}
