package com.teamk.scoretrack.module.security.handler;

import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.service.BadCredentialsAuthAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component(AuthSuccessHandler.NAME)
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String NAME = "authSuccessHandler";
    private final AuthenticationEntityService authenticationEntityService;
    private final AuthenticationHolderService authenticationHolderService;
    private final BadCredentialsAuthAttemptService badCredentialsAuthAttemptService;

    @Autowired
    public AuthSuccessHandler(AuthenticationEntityService authenticationEntityService, AuthenticationHolderService authenticationHolderService, BadCredentialsAuthAttemptService badCredentialsAuthAttemptService) {
        this.authenticationEntityService = authenticationEntityService;
        this.authenticationHolderService = authenticationHolderService;
        this.badCredentialsAuthAttemptService = badCredentialsAuthAttemptService;
        setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        authenticationHolderService.getCurrentAuthentication().ifPresent(a -> {
            badCredentialsAuthAttemptService.evict(a.getLoginname());
            a.setLastLogOn(Instant.now());
            authenticationEntityService.save(a);
        });
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
