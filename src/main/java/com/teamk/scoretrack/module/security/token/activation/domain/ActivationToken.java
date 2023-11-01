package com.teamk.scoretrack.module.security.token.activation.domain;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.domain.IdAware;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash(value = CacheStore.UUID_CACHE_STORE, timeToLive = ActivationToken.TTL)
public record ActivationToken(UUID id, String authId) implements Serializable, IdAware<UUID> {
    /**
     * In seconds.
     */
    public static final long TTL = 3600;

    @Override
    public UUID getId() {
        return id();
    }
}
