package com.teamk.scoretrack.module.security.token.crypto;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisAuthIdentifier;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;

// TODO: finish this
@RedisHash(value = CacheStore.CRYPTO_TOKENS, timeToLive = SecureRandomToken.TTL)
public class SecureRandomToken extends RedisAuthIdentifier {
    private final String value;
    public static final long TTL = 3600;
    @Serial
    private static final long serialVersionUID = 2522045508283620866L;

    public SecureRandomToken(String id, String value) {
        super(id);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
