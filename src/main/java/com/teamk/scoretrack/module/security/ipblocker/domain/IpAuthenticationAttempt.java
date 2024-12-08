package com.teamk.scoretrack.module.security.ipblocker.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisAuthIdentifier;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;

/**
 * @implNote {@link #getId()} is the ip address
 */
@RedisHash(value = CacheStore.IP_AUTH_FAILURE_CACHE_STORE, timeToLive = IpAuthenticationAttempt.TTL)
public class IpAuthenticationAttempt extends RedisAuthIdentifier {
    private int attempt;
    private Level level;
    private static final int MAX_FAILED_ATTEMPTS = 5;

    public static final long TTL = 7200;
    @Serial
    private static final long serialVersionUID = 8115899979061805470L;

    public IpAuthenticationAttempt(String id, int attempt) {
        super(id);
        this.attempt = attempt;
        this.level = Level.OK;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
        this.level = Level.byAttempt(attempt);
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Long getTtl() {
        return TTL;
    }

    public enum Level {
        OK(0,1, MAX_FAILED_ATTEMPTS - 1),
        WARN(1, MAX_FAILED_ATTEMPTS, (MAX_FAILED_ATTEMPTS * 2) - 1),
        BLOCK(2, MAX_FAILED_ATTEMPTS * 2, Integer.MAX_VALUE);

        private final int code;
        private final int low;
        private final int high;

        Level(int code, int low, int high) {
            this.code = code;
            if (low > high || low == high) {
                throw new IllegalArgumentException("Invalid bounds: low - %s, high - %s".formatted(low, high));
            }
            this.low = low;
            this.high = high;
        }

        @JsonValue
        public int getCode() {
            return code;
        }

        public int getLow() {
            return low;
        }

        public int getHigh() {
            return high;
        }

        public boolean isBlock() {
            return this == BLOCK;
        }

        @JsonCreator
        public static Level deserialize(int code) {
            for (Level level : Level.values()) {
                if (level.code == code) {
                    return level;
                }
            }
            return OK;
        }

        public static Level byAttempt(int attempt) {
            for (Level level : Level.values()) {
                if (level.low <= attempt && level.high >= attempt) {
                    return level;
                }
            }
            return BLOCK;
        }
    }
}
