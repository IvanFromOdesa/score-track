package com.teamk.scoretrack.module.security.handler.error.authfailure.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.dao.AuthenticationCredentialsFailureDao;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationCredentialsFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationCredentialsFailureService extends AbstractJpaEntityService<AuthenticationCredentialsFailure, Long, AuthenticationCredentialsFailureDao> {
    private final AuthenticationEntityService authenticationEntityService;

    @Autowired
    public AuthenticationCredentialsFailureService(AuthenticationEntityService authenticationEntityService) {
        this.authenticationEntityService = authenticationEntityService;
    }

    public void saveByLoginname(String loginname) {
        AuthenticationCredentialsFailure credentialsFailure = new AuthenticationCredentialsFailure();
        credentialsFailure.setAuthentication((AuthenticationBean) authenticationEntityService.loadUserByUsername(loginname));
        credentialsFailure.setUnlockedAt(Instant.now().plusSeconds(AuthenticationCredentialsFailure.LOCK_DURATION));
        save(credentialsFailure);
    }

    @Override
    @Autowired
    protected void setDao(AuthenticationCredentialsFailureDao dao) {
        this.dao = dao;
    }
}
