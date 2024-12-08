package com.teamk.scoretrack.module.security.session.controller;

import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.handler.ExtendedHandlerInterceptor;
import com.teamk.scoretrack.module.security.session.domain.SessionId;
import com.teamk.scoretrack.module.security.session.service.SessionExpirationAlertRedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import static com.teamk.scoretrack.module.commons.layout.preferences.PreferencesController.LANG;
import static com.teamk.scoretrack.module.commons.layout.preferences.PreferencesController.PREF;

@Component
public class SessionExpirationAlertRefreshInterceptor implements ExtendedHandlerInterceptor {
    private final SessionExpirationAlertRedisService expirationAlertRedisService;

    @Autowired
    public SessionExpirationAlertRefreshInterceptor(SessionExpirationAlertRedisService expirationAlertRedisService) {
        this.expirationAlertRedisService = expirationAlertRedisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isAuthenticated()) {
            if (new AntPathMatcher().match(PREF.concat(LANG).concat("/*"), request.getRequestURI())) {
                expirationAlertRedisService.evict(request.getSession().getId());
            }
        }
        return ExtendedHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (isAuthenticated()) {
            expirationAlertRedisService.cache(new SessionId(request.getSession().getId()));
        }
    }

    private static boolean isAuthenticated() {
        return !AuthenticationHolderService.isAnonymousAuthentication();
    }

    /**
     * Runs on every user request
     */
    @Override
    public String[] getPathPatterns() {
        return new String[]{"/**"};
    }
}
