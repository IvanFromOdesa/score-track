package com.teamk.scoretrack.module.security.token.activation.domain;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisIdAware;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@RedisHash(value = CacheStore.UUID_CACHE_STORE, timeToLive = ActivationToken.TTL)
public final class ActivationToken implements Serializable, RedisIdAware<UUID> {
    /**
     * In seconds.
     */
    public static final long TTL = 3600;
    @Serial
    private static final long serialVersionUID = 0L;
    private final UUID id;
    private final String authId;
    @TimeToLive
    private Long expiration;

    public ActivationToken(UUID id, String authId) {
        this.id = id;
        this.authId = authId;
    }

    @Override
    public UUID getId() {
        return id();
    }

    @Override
    public Long getExpiration() {
        return expiration;
    }

    @Override
    public Long getTtl() {
        return TTL;
    }

    public UUID id() {
        return id;
    }

    public String authId() {
        return authId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ActivationToken) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.authId, that.authId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authId);
    }

    @Override
    public String toString() {
        return "ActivationToken[" +
                "id=" + id + ", " +
                "authId=" + authId + ']';
    }

}
