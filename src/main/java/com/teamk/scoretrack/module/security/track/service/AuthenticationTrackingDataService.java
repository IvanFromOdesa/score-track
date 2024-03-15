package com.teamk.scoretrack.module.security.track.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.track.dao.AuthenticationTrackingDataDao;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.track.domain.AuthenticationTrackingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthenticationTrackingDataService extends AbstractJpaEntityService<AuthenticationTrackingData, Long, AuthenticationTrackingDataDao> {
    private final AuthenticationEntityService authenticationEntityService;

    @Autowired
    public AuthenticationTrackingDataService(AuthenticationEntityService authenticationEntityService) {
        this.authenticationEntityService = authenticationEntityService;
    }

    public Long update(AuthenticationBean authenticationBean, AuthenticationTrackingData e) {
        Optional<AuthenticationTrackingData> by = dao.findByAuthenticationBean(authenticationBean);
        if (by.isPresent()) {
            AuthenticationTrackingData trackingData = by.get();
            int consecutiveLogIn = trackingData.getConsecutiveLogIn();
            e.setConsecutiveLogIn(CommonsUtil.isInRange(e.getLastLogOn(), trackingData.getLastLogOn(), ChronoUnit.DAYS, 1, 2) ? consecutiveLogIn + 1 : consecutiveLogIn);
            e.setTotalLogIn(trackingData.getTotalLogIn() + 1);
            return save(merge(e, trackingData));
        }
        e.setConsecutiveLogIn(1);
        e.setTotalLogIn(1);
        e.setAuthenticationBean(authenticationBean);
        authenticationEntityService.save(authenticationBean);
        return save(e);
    }

    @Override
    @Autowired
    protected void setDao(AuthenticationTrackingDataDao dao) {
        this.dao = dao;
    }
}
