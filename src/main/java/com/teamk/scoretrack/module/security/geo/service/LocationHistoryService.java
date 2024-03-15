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
    public Optional<LocationHistory> findByCountryCityTrusted(AuthenticationBean authenticationBean, String country, String city) {
        return dao.findByCountryAndCityAndAuthenticationBeanTrusted(country, city, authenticationBean);
    }

    public List<LocationHistory> findTrusted(AuthenticationBean authenticationBean) {
        return dao.findByAuthenticationBeanTrusted(authenticationBean);
    }

    public List<LocationHistory> findByCountryCity(AuthenticationBean authenticationBean, String country, String city) {
        return dao.findByCountryAndCityAndAuthenticationBean(country, city, authenticationBean);
    }

    public boolean isFindByCountryCityTrusted(AuthenticationBean authenticationBean, String country, String city) {
        return dao.findByCountryAndCityAndAuthenticationBean(country, city, authenticationBean).stream().anyMatch(h -> h.getStatus().isResolved());
    }

    public Long saveTrusted(AuthenticationBean authenticationBean, GeoResponse geoResponse) {
        LocationHistory lc = new LocationHistory(authenticationBean, AuthenticationHistory.Status.RESOLVED);
        lc.setCountry(geoResponse.country());
        lc.setCity(geoResponse.city());
        lc.setLat(geoResponse.latitude());
        lc.setLng(geoResponse.longitude());
        return save(lc);
    }

    @Override
    @Autowired
    protected void setDao(LocationHistoryDao dao) {
        this.dao = dao;
    }
}
