package com.teamk.scoretrack.module.security.token.otp.domain;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisAuthIdentifier;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.time.Instant;

@RedisHash(value = CacheStore.OTP_CACHE_STORE, timeToLive = OTPToken.TTL)
public class OTPToken extends RedisAuthIdentifier {
    public static final long TTL = 1800;
    @Serial
    private static final long serialVersionUID = 3240090013634107441L;
    private final String otp;
    private final Long bhId;
    private final Instant created;

    /**
     * @param otp randomly generated recovery code
     * @param created creation time of token
     */
    public OTPToken(String id, String otp, Long bhId, Instant created) {
        super(id);
        this.otp = otp;
        this.bhId = bhId;
        this.created = created;
    }

    public String getOtp() {
        return otp;
    }

    public Long getBhId() {
        return bhId;
    }

    public Instant getCreated() {
        return created;
    }
}
