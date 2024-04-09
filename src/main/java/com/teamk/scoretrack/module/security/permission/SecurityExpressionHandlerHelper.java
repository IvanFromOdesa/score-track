package com.teamk.scoretrack.module.security.permission;

import com.teamk.scoretrack.module.security.acl.AclService;

import java.util.function.Supplier;

public interface SecurityExpressionHandlerHelper {
    void setAclServiceSupplier(Supplier<AclService> serviceSupplier);
}
