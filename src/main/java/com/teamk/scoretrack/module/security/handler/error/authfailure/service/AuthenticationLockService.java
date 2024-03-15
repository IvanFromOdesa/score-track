package com.teamk.scoretrack.module.security.handler.error.authfailure.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.dao.AuthenticationLockDao;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthenticationLockService extends AbstractJpaEntityService<AuthenticationLock, Long, AuthenticationLockDao> {
    private final AuthenticationEntityService authenticationEntityService;

    @Autowired
    public AuthenticationLockService(AuthenticationEntityService authenticationEntityService) {
        this.authenticationEntityService = authenticationEntityService;
    }

    public boolean lock(AuthenticationBean authentication, long lockDuration) {
        boolean recentAuthenticationPresent = authentication.isRecentAuthenticationPresent();
        if (!recentAuthenticationPresent) {
            Instant unlockedAt = Instant.now().plusSeconds(lockDuration);
            Optional<AuthenticationLock> byId = dao.findById(authentication.getId());
            if (byId.isPresent()) {
                AuthenticationLock lock = byId.get();
                if (lock.getUnlockedAt().isBefore(Instant.now())) {
                    lock.setUnlockedAt(unlockedAt);
                    save(lock);
                }
            } else {
                AuthenticationLock lock = new AuthenticationLock();
                lock.setAuthenticationBean(authentication);
                lock.setUnlockedAt(unlockedAt);
                authenticationEntityService.save(authentication);
                save(lock);
            }
            return true;
        }
        return false;
    }

    @Override
    @Autowired
    protected void setDao(AuthenticationLockDao dao) {
        this.dao = dao;
    }
}
