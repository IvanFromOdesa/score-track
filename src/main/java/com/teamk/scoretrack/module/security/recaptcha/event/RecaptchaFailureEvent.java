package com.teamk.scoretrack.module.security.recaptcha.event;

import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationWrapper;
import com.teamk.scoretrack.module.security.auth.event.AuthenticationFailureEvent;

import java.time.Instant;

public class RecaptchaFailureEvent extends AuthenticationFailureEvent<AuthenticationWrapper> {
    private String timestamp;
    private double score;
    private String action;

    public RecaptchaFailureEvent(NotificationEmail notificationEmail, Instant issuedAt) {
        super(notificationEmail, issuedAt, Cause.RECAPTCHA_FAILURE);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
