package com.teamk.scoretrack.module.security.auth.exception;

import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationCredentialsFailure;

public enum AuthenticationBlockedStatus {
    // TODO is this necessary?
    /**
     * 3+ unresolved {@link com.teamk.scoretrack.module.security.geo.domain.LocationHistory} with the same ip
     */
    BLOCKED_UNKNOWN_LOCATION("location.unknown"),
    /**
     * Unresolved {@link AuthenticationCredentialsFailure}
     */
    BLOCKED_AUTH_FAILURE("creds.locked"),
    BLOCKED_TOS("tos.block"),
    BLOCKED_SUSPICIOUS_ACTIVITY("activity.unusual"),
    DEFAULT("creds.invalid");

    private final String code;

    AuthenticationBlockedStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
