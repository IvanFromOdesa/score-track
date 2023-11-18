package com.teamk.scoretrack.module.security.auth.domain;

import org.springframework.security.core.userdetails.UserDetails;

public interface ExtendedUserDetails extends UserDetails {
    /**
     * Specifies if the {@link UserDetails} has been blocked due to bad credentials' authentication failures.
     * @return
     */
    boolean isBadCredentialsFailurePresent();
}
