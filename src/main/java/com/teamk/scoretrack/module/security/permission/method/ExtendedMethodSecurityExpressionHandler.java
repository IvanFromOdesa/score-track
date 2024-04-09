package com.teamk.scoretrack.module.security.permission.method;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Role;
import com.teamk.scoretrack.module.security.acl.AclService;
import com.teamk.scoretrack.module.security.permission.SecurityExpressionHandlerHelper;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

/**
 * <a href="https://github.com/spring-projects/spring-security/issues/12331">Issue</a>
 */
public class ExtendedMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler implements SecurityExpressionHandlerHelper {
    private Supplier<AclService> aclServiceSupplier;

    /**
     * Since overloaded createSecurityExpressionRoot is private, have to do this
     */
    @Override
    public EvaluationContext createEvaluationContext(Supplier<Authentication> authentication, MethodInvocation mi) {
        StandardEvaluationContext evaluationContext = (StandardEvaluationContext) super.createEvaluationContext(authentication, mi);
        evaluationContext.setRootObject(createSecurityExpressionRoot(authentication.get(), mi));
        return evaluationContext;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        final ExtendedMethodSecurityExpressionRoot expressionRoot = new ExtendedMethodSecurityExpressionRoot(authentication);
        expressionRoot.setAclService(aclServiceSupplier.get());
        expressionRoot.setPermissionEvaluator(getPermissionEvaluator());
        expressionRoot.setTrustResolver(this.getTrustResolver());
        expressionRoot.setRoleHierarchy(getRoleHierarchy());
        expressionRoot.setDefaultRolePrefix(Role.ROLE_PREFIX);
        return expressionRoot;
    }

    @Override
    public void setAclServiceSupplier(Supplier<AclService> aclServiceSupplier) {
        this.aclServiceSupplier = aclServiceSupplier;
    }
}
