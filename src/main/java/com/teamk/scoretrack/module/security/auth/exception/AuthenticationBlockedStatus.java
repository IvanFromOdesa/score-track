package com.teamk.scoretrack.module.security.auth.exception;

import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationLock;

public enum AuthenticationBlockedStatus {
    /**
     * Unresolved {@link AuthenticationLock}
     */
    BLOCKED_BAD_CREDENTIALS("auth.creds.locked"),
    BLOCKED_TOS("tos.block"),
    BLOCKED_SUSPICIOUS_ACTIVITY("activity.unusual"),
    DEFAULT("auth.error.default");

    private final String code;

    AuthenticationBlockedStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
