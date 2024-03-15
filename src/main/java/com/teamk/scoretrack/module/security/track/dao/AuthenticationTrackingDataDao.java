package com.teamk.scoretrack.module.security.track.dao;

import com.teamk.scoretrack.module.commons.base.dao.AbstractLongJpaDao;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.track.domain.AuthenticationTrackingData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationTrackingDataDao extends AbstractLongJpaDao<AuthenticationTrackingData> {
    Optional<AuthenticationTrackingData> findByAuthenticationBean(AuthenticationBean authenticationBean);
}
