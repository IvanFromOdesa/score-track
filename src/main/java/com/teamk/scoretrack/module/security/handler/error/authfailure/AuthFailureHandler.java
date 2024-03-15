package com.teamk.scoretrack.module.security.handler.error.authfailure;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.security.geo.service.GeoResponse;
import com.teamk.scoretrack.module.security.geo.service.IGeoLocationService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.event.BadCredentialsAuthFailureEvent;
import com.teamk.scoretrack.module.security.handler.error.authfailure.event.publisher.BadCredentialsAuthFailurePublisher;
import com.teamk.scoretrack.module.security.handler.error.authfailure.service.BadCredentialsAuthAttemptService;
import com.teamk.scoretrack.module.security.ipblocker.service.IpAuthenticationAttemptService;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

import static com.teamk.scoretrack.module.security.auth.controller.AuthenticationController.LOGIN;

@Component(AuthFailureHandler.NAME)
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public static final String NAME = "authFailureHandler";
    private final BadCredentialsAuthAttemptService badCredentialsAuthAttemptService;
    private final BadCredentialsAuthFailurePublisher badCredentialsAuthFailurePublisher;
    private final IpAuthenticationAttemptService ipAuthenticationAttemptService;
    private final IGeoLocationService geoLocationService;

    @Autowired
    public AuthFailureHandler(BadCredentialsAuthAttemptService badCredentialsAuthAttemptService,
                              BadCredentialsAuthFailurePublisher badCredentialsAuthFailurePublisher,
                              IpAuthenticationAttemptService ipAuthenticationAttemptService,
                              IGeoLocationService geoLocationService) {
        super(LOGIN.concat("?error"));
        this.badCredentialsAuthAttemptService = badCredentialsAuthAttemptService;
        this.badCredentialsAuthFailurePublisher = badCredentialsAuthFailurePublisher;
        this.ipAuthenticationAttemptService = ipAuthenticationAttemptService;
        this.geoLocationService = geoLocationService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String ip = HttpUtil.getClientIP(request);
        if (exception instanceof BadCredentialsException) {
            String loginname = request.getParameter("username");
            ipAuthenticationAttemptService.incrementFailureAttempt(ip);
            if (badCredentialsAuthAttemptService.incrementFailureAttempt(loginname)) {
                prepareProcessEvent(getGeoResponse(ip), request, ip, loginname);
            }
        } else if (exception instanceof UsernameNotFoundException) {
            ipAuthenticationAttemptService.incrementFailureAttempt(ip);
        }
        super.onAuthenticationFailure(request, response, exception);
    }

    private GeoResponse getGeoResponse(String ip) throws IOException {
        try {
            return geoLocationService.resolveLocation(ip);
        } catch (GeoIp2Exception e) {
            MessageLogger.error(e.getMessage());
        }
        return GeoResponse.UNKNOWN;
    }

    private void prepareProcessEvent(GeoResponse geoResponse, HttpServletRequest request, String ip, String loginname) {
        BadCredentialsAuthFailureEvent event = new BadCredentialsAuthFailureEvent(Instant.now());
        event.setAuthentication(loginname);
        event.setDefaults(geoResponse, ip, request);
        badCredentialsAuthFailurePublisher.processEvent(event);
    }
}
