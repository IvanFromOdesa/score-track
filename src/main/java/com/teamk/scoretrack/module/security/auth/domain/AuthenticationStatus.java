package com.teamk.scoretrack.module.security.auth.domain;

public enum AuthenticationStatus {
    CREATED,
    ACTIVATED,
    BLOCKED;

    public boolean isActivated() {
        return this == ACTIVATED;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }
}
