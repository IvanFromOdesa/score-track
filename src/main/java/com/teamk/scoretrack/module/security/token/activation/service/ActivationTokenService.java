package com.teamk.scoretrack.module.security.token.activation.service;

import com.teamk.scoretrack.module.commons.cache.redis.service.BaseRedisService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.token.activation.dao.ActivationTokenDao;
import com.teamk.scoretrack.module.security.token.activation.domain.ActivationToken;
import com.teamk.scoretrack.module.security.token.exception.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActivationTokenService extends BaseRedisService<AuthenticationBean, ActivationToken, UUID, ActivationTokenDao> {
    private final UUIDTokenResolverService uuidTokenResolverService;

    @Autowired
    public ActivationTokenService(UUIDTokenResolverService uuidTokenResolverService) {
        this.uuidTokenResolverService = uuidTokenResolverService;
    }

    public ActivationToken getActivationToken(UUID id) {
        return get(id).orElseThrow(TokenExpiredException::new);
    }

    public void evict(UUID id) {
        super.evict(id);
    }

    /**
     * @apiNote {@link AuthenticationBean} should be already persisted as we're retrieving its id
     */
    @Override
    protected ActivationToken fromContext(AuthenticationBean ctx) {
        UUID uuid = uuidTokenResolverService.generateToken(ctx);
        return new ActivationToken(uuid, ctx.getId().toString());
    }

    @Override
    @Autowired
    protected void setDao(ActivationTokenDao dao) {
        this.dao = dao;
    }
}
