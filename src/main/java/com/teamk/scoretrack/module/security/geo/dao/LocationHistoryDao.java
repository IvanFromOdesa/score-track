package com.teamk.scoretrack.module.security.geo.dao;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.history.dao.AbstractHistoryDao;
import com.teamk.scoretrack.module.security.geo.domain.LocationHistory;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public interface LocationHistoryDao extends AbstractHistoryDao<LocationHistory> {
    default Optional<LocationHistory> findByIpHashAndAuthenticationBeanTrusted(String ipHash, AuthenticationBean authenticationBean) {
        return findByIpHashAndAuthenticationBeanAndStatusIn(ipHash, authenticationBean, EnumSet.of(AuthenticationHistory.Status.TRUSTED));
    }

    default List<LocationHistory> findByAuthenticationBeanTrusted(AuthenticationBean authenticationBean) {
        return findByAuthenticationBeanAndStatusIn(authenticationBean, EnumSet.of(AuthenticationHistory.Status.TRUSTED));
    }

    List<LocationHistory> findByAuthenticationBeanAndStatusIn(AuthenticationBean authenticationBean, EnumSet<AuthenticationHistory.Status> statuses);
    List<LocationHistory> findByIpHashAndAuthenticationBean(String ipHash, AuthenticationBean authenticationBean);
    Optional<LocationHistory> findByIpHashAndAuthenticationBeanAndStatusIn(String ipHash, AuthenticationBean authenticationBean, EnumSet<AuthenticationHistory.Status> statuses);
}
