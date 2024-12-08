package com.teamk.scoretrack.module.security.session.domain;

import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisAuthIdentifier;

public class SessionExpirationAlert extends RedisAuthIdentifier {
    /**
     * Ttl is dynamic to be 0.9 of the sessionExpiration prop value
     */
    private final Long ttl;
    private final String value;

    public SessionExpirationAlert(String id, String value, Long ttl) {
        super(id);
        this.value = value;
        this.ttl = ttl;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Long getTtl() {
        return ttl;
    }
}
