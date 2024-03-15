package com.teamk.scoretrack.module.commons.mail.event;

import com.teamk.scoretrack.module.commons.mail.NotificationEmail;

import java.time.Instant;

// TODO: should extend ApplicationEvent
public abstract class EmailNotifyEvent<EMAIL_CONTEXT extends NotificationEmail> {
    protected final EMAIL_CONTEXT notificationEmail;
    protected final Instant issuedAt;
    protected final Cause cause;

    protected EmailNotifyEvent(EMAIL_CONTEXT notificationEmail, Instant issuedAt, Cause cause) {
        this.notificationEmail = notificationEmail;
        this.issuedAt = issuedAt;
        this.cause = cause;
    }

    public EMAIL_CONTEXT getNotificationEmail() {
        return notificationEmail;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public Cause getCause() {
        return cause;
    }

    public record Cause(String msg, int prio) {
        public static final int HIGHEST_PRIO = Integer.MAX_VALUE;
        public static final Cause UNKNOWN_LOCATION_AUTH = new Cause("User %s trying to authenticate from unusual location (%s) at %s (from device: %s, ip: %s) .", HIGHEST_PRIO);
        public static final Cause BAD_CREDENTIALS_AUTHENTICATION_FAILURE = new Cause("Too many auth failed attempts for user %s at %s (latest attempted device: %s, latest attempted ip: %s) .", HIGHEST_PRIO);
        public static final Cause UNUSUAL_ACTIVITIES = new Cause("Unusual activity detected on user account %s. Device: %s. Latest activities: %s, the last one at %s .", HIGHEST_PRIO - 1);
        public static final Cause RECAPTCHA_FAILURE = new Cause("Recaptcha failure for user %s. IP: %s, Device: %s.", HIGHEST_PRIO - 1);
        public static final Cause TOS_VIOLATION = new Cause("TOS violation by user %s. Reason: %s, timestamp: %s .", HIGHEST_PRIO - 2);
    }
}
