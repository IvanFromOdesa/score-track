package com.teamk.scoretrack.module.security.handler.error.authfailure.dao;

import com.teamk.scoretrack.module.commons.base.dao.AbstractLongJpaDao;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationCredentialsFailure;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationCredentialsFailureDao extends AbstractLongJpaDao<AuthenticationCredentialsFailure> {
}
