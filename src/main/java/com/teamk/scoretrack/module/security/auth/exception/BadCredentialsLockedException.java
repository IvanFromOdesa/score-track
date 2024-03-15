package com.teamk.scoretrack.module.security.auth.exception;

import com.teamk.scoretrack.module.security.auth.domain.ExtendedUserDetails;

/**
 * Thrown if the {@link ExtendedUserDetails#isBadCredentialsFailurePresent()} returns false
 */
public class BadCredentialsLockedException extends AuthenticationFailureException {
    public BadCredentialsLockedException(String msg) {
        super(msg);
    }

    @Override
    public AuthenticationBlockedStatus getBlockStatus() {
        return AuthenticationBlockedStatus.BLOCKED_BAD_CREDENTIALS;
    }
}
