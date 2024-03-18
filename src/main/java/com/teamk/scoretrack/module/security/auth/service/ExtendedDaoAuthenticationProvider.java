package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.ExtendedUserDetails;
import com.teamk.scoretrack.module.security.auth.exception.BadCredentialsLockedException;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class ExtendedDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private final AuthenticationSignUpService authenticationSignUpService;

    @Autowired
    public ExtendedDaoAuthenticationProvider(AuthenticationSignUpService authenticationSignUpService) {
        this.authenticationSignUpService = authenticationSignUpService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        if (((ExtendedUserDetails) userDetails).isBadCredentialsFailurePresent()) {
            throw new BadCredentialsLockedException("Locked account due to recent auth attempts with bad credentials.");
        } else if (!userDetails.isEnabled()) {
            logger.debug("Failed to authenticate since user account is disabled");
            authenticationSignUpService.sendActivationEmail((AuthenticationBean) userDetails, HttpUtil.getBaseUrl().concat(AuthenticationController.ACTIVATE));
            throw new DisabledException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
        }
    }
}
