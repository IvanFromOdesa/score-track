package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.security.auth.domain.ExtendedUserDetails;
import com.teamk.scoretrack.module.security.auth.exception.BadCredentialsLockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class ExtendedDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (((ExtendedUserDetails) userDetails).isBadCredentialsFailurePresent()) {
            throw new BadCredentialsLockedException("Locked account due to 5 recent auth attempts with bad credentials.");
        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
