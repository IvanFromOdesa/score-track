package com.teamk.scoretrack.module.security.pwdreset.domain;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisAuthIdentifier;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;

@RedisHash(value = CacheStore.CRYPTO_TOKENS, timeToLive = PwdResetToken.TTL)
public class PwdResetToken extends RedisAuthIdentifier {
    private final String value;
    private final String email;
    private Status status;
    public static final long TTL = 300;
    @Serial
    private static final long serialVersionUID = 2522045508283620866L;

    public PwdResetToken(String id, String value, String email) {
        super(id);
        this.value = value;
        this.email = email;
        this.status = Status.CREATED;
    }

    public String getValue() {
        return value;
    }

    public String getEmail() {
        return email;
    }

    public boolean isUsed() {
        return status.isUsed();
    }

    public void markUsed() {
        this.status = Status.USED;
    }

    @Override
    public Long getTtl() {
        return TTL;
    }

    public enum Status {
        CREATED, USED;

        private boolean isUsed() {
            return this == Status.USED;
        }
    }
}
