package com.teamk.scoretrack.module.security.handler.error.authfailure.event;

import com.teamk.scoretrack.module.security.auth.event.AuthenticationFailureEvent;

import java.time.Instant;

public class BadCredentialsAuthFailureEvent extends AuthenticationFailureEvent<String> {
    public BadCredentialsAuthFailureEvent(Instant issuedAt) {
        super(issuedAt, Cause.BAD_CREDENTIALS_AUTHENTICATION_FAILURE);
    }
}
