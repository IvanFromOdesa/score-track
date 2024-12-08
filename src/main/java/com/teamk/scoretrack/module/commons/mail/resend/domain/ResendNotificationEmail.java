package com.teamk.scoretrack.module.commons.mail.resend.domain;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisAuthIdentifier;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;

/**
 * Cached emails to be resent if needed.
 */
@RedisHash(value = CacheStore.MAIL_CACHE_STORE, timeToLive = ResendNotificationEmail.TTL)
public class ResendNotificationEmail extends RedisAuthIdentifier {
    public static final long TTL = 1800;
    @Serial
    private static final long serialVersionUID = 7284184782177678169L;
    private final NotificationEmail email;
    private int attempt;

    public ResendNotificationEmail(NotificationEmail email, String id) {
        super(id);
        this.email = email;
    }

    public NotificationEmail getEmail() {
        return email;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public int getMaxAttempts() {
        return 3;
    }

    @Override
    public Long getTtl() {
        return TTL;
    }
}
