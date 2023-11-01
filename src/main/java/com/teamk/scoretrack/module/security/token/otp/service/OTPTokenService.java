package com.teamk.scoretrack.module.security.token.otp.service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.service.HashedRedisService;
import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.security.token.otp.ctx.OTPAuthContext;
import com.teamk.scoretrack.module.security.token.otp.domain.OTPToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.time.Duration;
import java.time.Instant;

@Service
public class OTPTokenService extends HashedRedisService<OTPAuthContext, OTPToken, String> {
    public OTPTokenService(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    /**
     * Key length should match the length of the HMAC output (160 bits for SHA-1, 256 bits
     * for SHA-256, and 512 bits for SHA-512). Note that while {@link Mac#getMacLength()} returns a
     * length in _bytes,_ {@link KeyGenerator#init(int)} takes a key length in _bits._
     */
    private String generateOTPCode(Instant timestamp) {
        try {
            TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(1), 8, TimeBasedOneTimePasswordGenerator.TOTP_ALGORITHM_HMAC_SHA256);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
            int macLengthInBytes = Mac.getInstance(totp.getAlgorithm()).getMacLength();
            keyGenerator.init(macLengthInBytes * 8);
            return totp.generateOneTimePasswordString(keyGenerator.generateKey(), timestamp);
        } catch (Exception e) {
            MessageLogger.error(e.getMessage(), e);
            throw new ServerException(e);
        }
    }

    @Override
    protected OTPToken fromContext(OTPAuthContext ctx) {
        Instant now = Instant.now();
        return new OTPToken(generateOTPCode(now), ctx.authId(), ctx.bhId(), now);
    }

    @Override
    protected String provideHashStore() {
        return CacheStore.OTP_CACHE_STORE;
    }
}
