package com.teamk.scoretrack.module.security.handler.error.authfailure.service;

import com.teamk.scoretrack.module.commons.cache.redis.service.BaseRedisService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.dao.BadCredentialsAuthAttemptDao;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.BadCredentialsAuthenticationAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BadCredentialsAuthAttemptService extends BaseRedisService<BadCredentialsAuthenticationAttempt, BadCredentialsAuthenticationAttempt, String, BadCredentialsAuthAttemptDao> {
    public boolean incrementFailureAttempt(String loginname) {
        Optional<BadCredentialsAuthenticationAttempt> byId = get(loginname);
        if (byId.isPresent()) {
            BadCredentialsAuthenticationAttempt bc = byId.get();
            int attempt = bc.getAttempt() + 1;
            if (attempt < BadCredentialsAuthenticationAttempt.MAX_FAILED_ATTEMPTS) {
                bc.setAttempt(attempt);
                cache(bc);
            } else {
                evict(loginname);
                return true;
            }
        } else {
            cache(new BadCredentialsAuthenticationAttempt(loginname, 1));
        }
        return false;
    }

    @Override
    protected BadCredentialsAuthenticationAttempt fromContext(BadCredentialsAuthenticationAttempt ctx) {
        return ctx;
    }

    @Override
    @Autowired
    protected void setDao(BadCredentialsAuthAttemptDao dao) {
        this.dao = dao;
    }
}
