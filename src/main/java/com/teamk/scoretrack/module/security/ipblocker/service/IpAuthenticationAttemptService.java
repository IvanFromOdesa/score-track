package com.teamk.scoretrack.module.security.ipblocker.service;

import com.teamk.scoretrack.module.commons.cache.redis.service.RedisEqualCtxService;
import com.teamk.scoretrack.module.security.ipblocker.dao.IpAuthenticationAttemptDao;
import com.teamk.scoretrack.module.security.ipblocker.domain.IpAuthenticationAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IpAuthenticationAttemptService extends RedisEqualCtxService<IpAuthenticationAttempt, String, IpAuthenticationAttemptDao> {

    public boolean incrementFailureAttempt(String ip) {
        Optional<IpAuthenticationAttempt> byIp = get(ip);
        if (byIp.isPresent()) {
            IpAuthenticationAttempt authAttempt = byIp.get();
            IpAuthenticationAttempt.Level level = authAttempt.getLevel();
            if (!level.isBlock()) {
                authAttempt.setAttempt(authAttempt.getAttempt() + 1);
                cache(authAttempt);
            }
            return level.isBlock();
        } else {
            cache(new IpAuthenticationAttempt(ip, 1));
        }
        return false;
    }

    public boolean isBlocked(String ip) {
        Optional<IpAuthenticationAttempt> byIp = get(ip);
        return byIp.isPresent() && byIp.get().getLevel().isBlock();
    }

    @Override
    @Autowired
    protected void setDao(IpAuthenticationAttemptDao dao) {
        this.dao = dao;
    }
}
