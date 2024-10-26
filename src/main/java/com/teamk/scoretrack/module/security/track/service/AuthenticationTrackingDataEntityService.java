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
public class AuthenticationTrackingDataEntityService extends AbstractJpaEntityService<AuthenticationTrackingData, Long, AuthenticationTrackingDataDao> {
    private final AuthenticationEntityService authenticationEntityService;

    @Autowired
    public AuthenticationTrackingDataEntityService(AuthenticationEntityService authenticationEntityService) {
        this.authenticationEntityService = authenticationEntityService;
    }

    public AuthenticationTrackingData update(AuthenticationBean authenticationBean, AuthenticationTrackingData e) {
        Optional<AuthenticationTrackingData> by = dao.findByAuthenticationBean(authenticationBean);
        if (by.isPresent()) {
            AuthenticationTrackingData trackingData = by.get();
            int consecutiveLogIn = trackingData.getConsecutiveLogIn();
            e.setConsecutiveLogIn(
                    CommonsUtil.isInRange(e.getLastLogOn(), trackingData.getLastLogOn(),
                            ChronoUnit.DAYS, 1, 2)
                            ? consecutiveLogIn + 1 : 1);
            e.setTotalLogIn(trackingData.getTotalLogIn() + 1);
            AuthenticationTrackingData merged = merge(e, trackingData);
            this.save(merged);
            return merged;
        }
        e.setConsecutiveLogIn(1);
        e.setTotalLogIn(1);
        e.setAuthenticationBean(authenticationBean);
        authenticationEntityService.save(authenticationBean);
        this.save(e);
        return e;
    }

    @Override
    @Autowired
    protected void setDao(AuthenticationTrackingDataDao dao) {
        this.dao = dao;
    }
}
