package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.pwdreset.service.PwdResetService;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

import static com.teamk.scoretrack.module.security.pwdreset.controller.PwdResetController.CONFIRM_URL_TOKEN;

@Component(PostAuthenticationChecks.NAME)
public class PostAuthenticationChecks implements UserDetailsChecker {
    public static final String NAME = "postAuthChecks";
    private static final Logger LOGGER = LoggerFactory.getLogger(PostAuthenticationChecks.class);
    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private PwdResetService pwdResetService;

    @Override
    public void check(UserDetails user) {
        if (!user.isCredentialsNonExpired()) {
            HttpServletRequest request = HttpUtil.getCurrentHttpRequest();
            if (request == null) {
                throw new ServerException("No HTTP request context");
            }
            pwdResetService.requestPasswordReset(HttpUtil.getBaseUrl().concat(CONFIRM_URL_TOKEN), ((AuthenticationBean) user).getEmail(), HttpUtil.getClientIP(request));
            LOGGER.debug("Failed to authenticate since user account credentials have expired");
            throw new CredentialsExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
        }
    }

    @Autowired
    public void setPwdResetService(PwdResetService pwdResetService) {
        this.pwdResetService = pwdResetService;
    }
}
