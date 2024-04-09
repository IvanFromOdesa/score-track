package com.teamk.scoretrack.module.security.permission.method;

import com.teamk.scoretrack.module.security.acl.AclService;
import com.teamk.scoretrack.module.security.permission.ExtendedSecurityExpressionOperations;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class ExtendedMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations, ExtendedSecurityExpressionOperations {
    private AclService aclService;
    private Object filterObject;
    private Object returnObject;

    public ExtendedMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public void setAclService(AclService aclService) {
        this.aclService = aclService;
    }

    @Override
    public AclService getAclService() {
        return aclService;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }
}
