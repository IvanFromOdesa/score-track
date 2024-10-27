package com.teamk.scoretrack.module.security.oauth2.external;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalAuthenticationEntityService extends AbstractJpaEntityService<ExternalAuthentication, ExternalAuthentication.ExternalAuthenticationId, ExternalAuthenticationDao> {
    public Optional<ExternalAuthentication> getByAuthenticationBeanAndExternalId(AuthenticationBean authenticationBean, String externalId) {
        return dao.findByAuthenticationBeanAndExternalId(authenticationBean, externalId);
    }

    @Override
    @Autowired
    protected void setDao(ExternalAuthenticationDao dao) {
        this.dao = dao;
    }
}
