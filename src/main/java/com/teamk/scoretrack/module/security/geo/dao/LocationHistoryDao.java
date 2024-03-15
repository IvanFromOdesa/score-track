package com.teamk.scoretrack.module.security.geo.dao;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.history.dao.AbstractHistoryDao;
import com.teamk.scoretrack.module.security.geo.domain.LocationHistory;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public interface LocationHistoryDao extends AbstractHistoryDao<LocationHistory> {
    default Optional<LocationHistory> findByCountryAndCityAndAuthenticationBeanTrusted(String country, String city, AuthenticationBean authenticationBean) {
        return findByCountryAndCityAndAuthenticationBeanAndStatusIn(country, city, authenticationBean, EnumSet.of(AuthenticationHistory.Status.RESOLVED));
    }

    default List<LocationHistory> findByAuthenticationBeanTrusted(AuthenticationBean authenticationBean) {
        return findByAuthenticationBeanAndStatusIn(authenticationBean, EnumSet.of(AuthenticationHistory.Status.RESOLVED));
    }

    List<LocationHistory> findByAuthenticationBeanAndStatusIn(AuthenticationBean authenticationBean, EnumSet<AuthenticationHistory.Status> statuses);
    List<LocationHistory> findByCountryAndCityAndAuthenticationBean(String country, String city, AuthenticationBean authenticationBean);
    Optional<LocationHistory> findByCountryAndCityAndAuthenticationBeanAndStatusIn(String country, String city, AuthenticationBean authenticationBean, EnumSet<AuthenticationHistory.Status> statuses);
}
