package com.teamk.scoretrack.module.security.handler;

import com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptions;
import com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptionsUtils;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.service.BadCredentialsAuthAttemptService;
import com.teamk.scoretrack.module.security.ipblocker.service.IpAuthenticationAttemptService;
import com.teamk.scoretrack.module.security.recaptcha.service.RecaptchaResponseResolveService;
import com.teamk.scoretrack.module.security.token.otp.controller.OtpRedirectHandler;
import com.teamk.scoretrack.module.security.track.domain.AuthenticationTrackingData;
import com.teamk.scoretrack.module.security.track.service.AuthenticationTrackingDataEntityService;
import com.teamk.scoretrack.module.security.util.HttpUtil;
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
    private final AuthenticationTrackingDataEntityService authenticationTrackingDataEntityService;
    private final AuthenticationHolderService authenticationHolderService;
    private final BadCredentialsAuthAttemptService badCredentialsAuthAttemptService;
    private final IpAuthenticationAttemptService ipAuthenticationAttemptService;
    private final RecaptchaResponseResolveService recaptchaResponseResolveService;

    @Autowired
    public AuthSuccessHandler(AuthenticationTrackingDataEntityService authenticationTrackingDataEntityService,
                              AuthenticationHolderService authenticationHolderService,
                              BadCredentialsAuthAttemptService badCredentialsAuthAttemptService,
                              IpAuthenticationAttemptService ipAuthenticationAttemptService,
                              RecaptchaResponseResolveService recaptchaResponseResolveService) {
        this.authenticationTrackingDataEntityService = authenticationTrackingDataEntityService;
        this.authenticationHolderService = authenticationHolderService;
        this.badCredentialsAuthAttemptService = badCredentialsAuthAttemptService;
        this.ipAuthenticationAttemptService = ipAuthenticationAttemptService;
        this.recaptchaResponseResolveService = recaptchaResponseResolveService;
        setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        authenticationHolderService.getCurrentAuthentication().ifPresent(a -> {
            clear(HttpUtil.getClientIP(request), a.getLoginname());
            AuthenticationTrackingData track = new AuthenticationTrackingData();
            track.setLastLogOn(Instant.now());
            if (authenticationTrackingDataEntityService.update(a, track).getTotalLogIn() == 1) {
                UiAlertDisplayOptionsUtils.addToHttpSession(request.getSession(), UiAlertDisplayOptions::setFirstLogIn);
            }
        });
        if (recaptchaResponseResolveService.resolve(request, HttpUtil.getClientIP(request)).isBlocked()) {
            OtpRedirectHandler.onBlockStatus(request, response);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private void clear(String ip, String loginname) {
        badCredentialsAuthAttemptService.evict(loginname);
        ipAuthenticationAttemptService.evict(ip);
    }
}
