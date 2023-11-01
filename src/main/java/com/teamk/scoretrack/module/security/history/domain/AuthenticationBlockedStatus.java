package com.teamk.scoretrack.module.security.history.domain;

public enum AuthenticationBlockedStatus {
    BLOCKED_UNKNOWN_LOCATION("location.unknown"),
    BLOCKED_AUTH_FAILURE("auth.failure"),
    BLOCKED_TOS("tos.block"),
    BLOCKED_SUSPICIOUS_ACTIVITY("activity.unusual"),
    DEFAULT("");

    private final String cause;

    AuthenticationBlockedStatus(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
