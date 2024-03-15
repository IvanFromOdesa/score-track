package com.teamk.scoretrack.module.security.handler.error.authfailure.domain;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisAuthIdentifier;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;

/**
 * @implNote {@link #getId()} is the {@link AuthenticationBean#getLoginname()}
 */
@RedisHash(value = CacheStore.BAD_CREDENTIALS_FAILURE_CACHE_STORE, timeToLive = BadCredentialsAuthenticationAttempt.TTL)
public class BadCredentialsAuthenticationAttempt extends RedisAuthIdentifier {
    private int attempt;
    /**
     * If the auth attempt consecutively fails within this period, increment failure attempt until {@link #MAX_FAILED_ATTEMPTS}
     */
    public static final long TTL = 3600;
    public static final int MAX_FAILED_ATTEMPTS = 5;
    public static final long LOCK_DURATION = 60 * 60 * 8; // In seconds
    @Serial
    private static final long serialVersionUID = -7775678253713337170L;

    public BadCredentialsAuthenticationAttempt(String id, int attempt) {
        super(id);
        this.attempt = attempt;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }
}
