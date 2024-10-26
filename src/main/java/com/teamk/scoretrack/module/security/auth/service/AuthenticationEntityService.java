package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.security.auth.dao.AuthenticationDao;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.history.service.AuthenticationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Changed on 18.03.2024 - following SOLID patterns, decouple sign up logic into {@link AuthenticationSignUpService}
 */
@Service
public class AuthenticationEntityService extends AbstractJpaEntityService<AuthenticationBean, Long, AuthenticationDao> implements UserDetailsService {
    private final AuthenticationHistoryService historyService;

    @Autowired
    public AuthenticationEntityService(AuthenticationHistoryService historyService) {
        this.historyService = historyService;
    }

    public Long addAuthHistory(AuthenticationHistory history) {
        return baseTransactionManager.doInNewTransaction(status -> historyService.save(history));
    }

    public boolean resolveAuthHistory(AuthenticationBean authenticationBean, Long id) {
        return historyService.resolveAuthHistory(authenticationBean, id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return dao.findByLoginname(username).orElseThrow(() -> new UsernameNotFoundException("%s not found.".formatted(username)));
    }

    public boolean existsByLoginnameOrEmail(String loginname, String email) {
        return dao.existsByLoginnameOrEmail(loginname, email);
    }

    public Optional<AuthenticationBean> findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    @Autowired
    protected void setDao(AuthenticationDao dao) {
        this.dao = dao;
    }
}
