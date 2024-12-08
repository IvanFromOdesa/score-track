package com.teamk.scoretrack.module.security.handler;

import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.service.BadCredentialsAuthAttemptService;
import com.teamk.scoretrack.module.security.ipblocker.service.IpAuthenticationAttemptService;
import com.teamk.scoretrack.module.security.recaptcha.service.RecaptchaResponseResolveService;
import com.teamk.scoretrack.module.security.session.service.SessionExpirationAlertRedisService;
import com.teamk.scoretrack.module.security.track.service.AuthenticationTrackingDataEntityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component(OAuth2SuccessHandler.NAME)
public class OAuth2SuccessHandler extends AuthSuccessHandler {
    public static final String NAME = "oAuth2SuccessHandler";
    private final AuthenticationEntityService authenticationEntityService;

    @Autowired
    public OAuth2SuccessHandler(AuthenticationTrackingDataEntityService authenticationTrackingDataEntityService,
                                AuthenticationHolderService authenticationHolderService,
                                BadCredentialsAuthAttemptService badCredentialsAuthAttemptService,
                                IpAuthenticationAttemptService ipAuthenticationAttemptService,
                                RecaptchaResponseResolveService recaptchaResponseResolveService,
                                AuthenticationEntityService authenticationEntityService,
                                SessionExpirationAlertRedisService sessionExpirationAlertRedisService) {
        super(
                authenticationTrackingDataEntityService,
                authenticationHolderService,
                badCredentialsAuthAttemptService,
                ipAuthenticationAttemptService,
                recaptchaResponseResolveService,
                sessionExpirationAlertRedisService
        );
        this.authenticationEntityService = authenticationEntityService;
        this.setRequireRecaptchaCheck(false);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        authenticationHolderService.getCurrentOAuth2Token().ifPresent(a -> {
            String email = a.getPrincipal().getAttribute("email");
            authenticationEntityService.findByEmail(email).ifPresentOrElse(ab -> {
                UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                        ab,
                        ab.getPassword(),
                        ab.getAuthorities()
                );
                authenticationHolderService.setAuthentication(newAuth);
            }, () -> { throw new ServerException("Email from oauth2 was not found"); });
        });
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
