package com.teamk.scoretrack.module.security.auth.exception;

import org.springframework.security.core.AuthenticationException;

public abstract class AuthenticationFailureException extends AuthenticationException {
    public AuthenticationFailureException(String msg) {
        super(msg);
    }

    public abstract AuthenticationBlockedStatus getBlockStatus();
}
