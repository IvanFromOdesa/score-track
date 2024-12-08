package com.teamk.scoretrack.module.security.session.service;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.service.RedisTemplateBaseService;
import com.teamk.scoretrack.module.security.session.config.SessionConfig;
import com.teamk.scoretrack.module.security.session.domain.SessionExpirationAlert;
import com.teamk.scoretrack.module.security.session.domain.SessionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SessionExpirationAlertRedisService extends RedisTemplateBaseService<SessionId, SessionExpirationAlert> {
    @Value("${redis.session.lifespan}")
    private long redisSessionLifespan;

    @Override
    protected SessionExpirationAlert fromContext(SessionId ctx) {
        String sessionId = ctx.value();
        return new SessionExpirationAlert(sessionId, sessionId, (long) ((redisSessionLifespan / 1000) * 0.9));
    }

    @Override
    protected String getNamespace() {
        return String.format("%s:%s", CacheStore.SESSION_EXPIRATION_ALERT, LocaleContextHolder.getLocale().getLanguage());
    }

    @Override
    @Autowired
    protected void setRedisTemplate(@Qualifier(SessionConfig.REDIS_SESSION_TEMPLATE) RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
