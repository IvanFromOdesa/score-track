package com.teamk.scoretrack.module.security.handler;

import com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptions;
import com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptionsUtils;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.service.BadCredentialsAuthAttemptService;
import com.teamk.scoretrack.module.security.ipblocker.service.IpAuthenticationAttemptService;
import com.teamk.scoretrack.module.security.recaptcha.service.RecaptchaResponseResolveService;
import com.teamk.scoretrack.module.security.session.domain.SessionId;
import com.teamk.scoretrack.module.security.session.service.SessionExpirationAlertRedisService;
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
    protected final AuthenticationTrackingDataEntityService authenticationTrackingDataEntityService;
    protected final AuthenticationHolderService authenticationHolderService;
    protected final BadCredentialsAuthAttemptService badCredentialsAuthAttemptService;
    protected final IpAuthenticationAttemptService ipAuthenticationAttemptService;
    protected final RecaptchaResponseResolveService recaptchaResponseResolveService;
    protected final SessionExpirationAlertRedisService sessionExpirationAlertRedisService;
    private boolean requireRecaptchaCheck;

    @Autowired
    public AuthSuccessHandler(AuthenticationTrackingDataEntityService authenticationTrackingDataEntityService,
                              AuthenticationHolderService authenticationHolderService,
                              BadCredentialsAuthAttemptService badCredentialsAuthAttemptService,
                              IpAuthenticationAttemptService ipAuthenticationAttemptService,
                              RecaptchaResponseResolveService recaptchaResponseResolveService,
                              SessionExpirationAlertRedisService sessionExpirationAlertRedisService) {
        this.authenticationTrackingDataEntityService = authenticationTrackingDataEntityService;
        this.authenticationHolderService = authenticationHolderService;
        this.badCredentialsAuthAttemptService = badCredentialsAuthAttemptService;
        this.ipAuthenticationAttemptService = ipAuthenticationAttemptService;
        this.recaptchaResponseResolveService = recaptchaResponseResolveService;
        this.sessionExpirationAlertRedisService = sessionExpirationAlertRedisService;
        this.requireRecaptchaCheck = true;
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

        sessionExpirationAlertRedisService.cache(new SessionId(request.getSession().getId()));

        if (requireRecaptchaCheck && recaptchaResponseResolveService.resolve(request, HttpUtil.getClientIP(request)).isBlocked()) {
            OtpRedirectHandler.onBlockStatus(request, response);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private void clear(String ip, String loginname) {
        badCredentialsAuthAttemptService.evict(loginname);
        ipAuthenticationAttemptService.evict(ip);
    }

    public boolean isRequireRecaptchaCheck() {
        return requireRecaptchaCheck;
    }

    public void setRequireRecaptchaCheck(boolean requireRecaptchaCheck) {
        this.requireRecaptchaCheck = requireRecaptchaCheck;
    }
}
