package com.teamk.scoretrack.module.security.commons.config;

import com.teamk.scoretrack.module.security.acl.AclService;
import com.teamk.scoretrack.module.security.permission.method.ExtendedMethodSecurityExpressionHandler;
import com.teamk.scoretrack.module.security.permission.web.ExtendedWebSecurityExpressionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
public class SecurityExpressionConfiguration {
    @Bean
    protected static MethodSecurityExpressionHandler methodSecurityExpressionHandler(AclService aclService) {
        ExtendedMethodSecurityExpressionHandler securityExpressionHandler = new ExtendedMethodSecurityExpressionHandler();
        securityExpressionHandler.setAclServiceSupplier(() -> aclService);
        return securityExpressionHandler;
    }

    @Bean
    protected static DefaultWebSecurityExpressionHandler extendedWebSecurityExpressionHandler(AclService aclService) {
        ExtendedWebSecurityExpressionHandler securityExpressionHandler = new ExtendedWebSecurityExpressionHandler();
        securityExpressionHandler.setAclServiceSupplier(() -> aclService);
        return securityExpressionHandler;
    }
}
