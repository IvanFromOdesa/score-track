package com.teamk.scoretrack.module.security.history.dao;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthenticationHistoryDao extends AbstractHistoryDao<AuthenticationHistory> {
    Optional<AuthenticationHistory> findByIdAndAuthenticationBean(Long id, AuthenticationBean authenticationBean);
    default List<AuthenticationHistory> findByAuthenticationBeanAndStatusBlocked(AuthenticationBean authenticationBean, Pageable pageable) {
        // Limit
        return findByAuthenticationBeanAndStatusInOrderByIssuedAt(authenticationBean, EnumSet.of(AuthenticationHistory.Status.BLOCKED), pageable);
    }
    List<AuthenticationHistory> findByAuthenticationBeanAndStatusInOrderByIssuedAt(AuthenticationBean authenticationBean, EnumSet<AuthenticationHistory.Status> statuses, Pageable pageable);
}
