package com.teamk.scoretrack.module.security.auth.domain.audit;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationBeanAuditorAware implements AuditorAware<AuthenticationBean> {
    private final AuthenticationHolderService authenticationHolderService;

    @Autowired
    public AuthenticationBeanAuditorAware(AuthenticationHolderService authenticationHolderService) {
        this.authenticationHolderService = authenticationHolderService;
    }

    @Override
    public Optional<AuthenticationBean> getCurrentAuditor() {
        return authenticationHolderService.getCurrentAuthentication();
    }
}
