package com.teamk.scoretrack.module.security.geo.service;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.geo.dao.LocationHistoryDao;
import com.teamk.scoretrack.module.security.geo.domain.LocationHistory;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.history.service.AbstractHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationHistoryService extends AbstractHistoryService<LocationHistory, LocationHistoryDao> {
    public Optional<LocationHistory> findByIpTrusted(AuthenticationBean authenticationBean, String hashedIp) {
        return dao.findByIpHashAndAuthenticationBeanTrusted(hashedIp, authenticationBean);
    }

    public List<LocationHistory> findTrusted(AuthenticationBean authenticationBean) {
        return dao.findByAuthenticationBeanTrusted(authenticationBean);
    }

    public List<LocationHistory> findByIp(AuthenticationBean authenticationBean, String hashedIp) {
        return dao.findByIpHashAndAuthenticationBean(hashedIp, authenticationBean);
    }

    public boolean isFindByIpTrusted(AuthenticationBean authenticationBean, String hashedIp) {
        return dao.findByIpHashAndAuthenticationBean(hashedIp, authenticationBean).stream().anyMatch(h -> h.getStatus().isTrusted());
    }

    public Long saveTrusted(AuthenticationBean authenticationBean, String hashedIp) {
        LocationHistory lc = new LocationHistory(authenticationBean, AuthenticationHistory.Status.TRUSTED);
        lc.setIpHash(hashedIp);
        return save(lc);
    }

    @Override
    @Autowired
    protected void setDao(LocationHistoryDao dao) {
        this.dao = dao;
    }
}
