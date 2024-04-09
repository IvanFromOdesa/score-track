package com.teamk.scoretrack.module.security.permission.web;

import com.teamk.scoretrack.module.security.acl.AclService;
import com.teamk.scoretrack.module.security.permission.ExtendedSecurityExpressionOperations;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import java.util.function.Supplier;

public class ExtendedWebSecurityExpressionRoot extends WebSecurityExpressionRoot implements ExtendedSecurityExpressionOperations {
    private AclService aclService;

    public ExtendedWebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
        super(a, fi);
    }

    public ExtendedWebSecurityExpressionRoot(Supplier<Authentication> authentication, HttpServletRequest request) {
        super(authentication, request);
    }

    @Override
    public AclService getAclService() {
        return aclService;
    }

    public void setAclService(AclService aclService) {
        this.aclService = aclService;
    }
}
