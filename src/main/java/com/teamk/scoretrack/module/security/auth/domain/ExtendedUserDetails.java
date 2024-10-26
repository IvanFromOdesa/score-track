package com.teamk.scoretrack.module.security.auth.domain;

import org.springframework.security.core.userdetails.UserDetails;

public interface ExtendedUserDetails extends UserDetails {
    /**
     * Specifies if the {@link UserDetails} has been blocked due to bad credentials' authentication failures.
     * // This is the same as isAccountNonLocked(), prob should be removed
     * @return
     */
    boolean isBadCredentialsFailurePresent();

    /**
     * Recent authentication activity
     * @return
     */
    boolean isRecentAuthenticationPresent();

    /**
     * Checks if the {@link UserDetails} has been confirmed.
     * @return
     */
    boolean isLastConfirmed();
}
