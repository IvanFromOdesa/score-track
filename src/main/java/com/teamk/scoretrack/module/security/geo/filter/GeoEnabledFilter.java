package com.teamk.scoretrack.module.security.geo.filter;

import com.teamk.scoretrack.module.security.geo.service.ULHService;
import com.teamk.scoretrack.module.security.handler.error.AuthControllerAdvice;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.token.otp.controller.OtpAuthController;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@ConditionalOnProperty(value = "geo.enabled", havingValue = "true")
public class GeoEnabledFilter extends GeoLiteFilter {
    private static final String GEO_FILTERED = "GEO_FILTERED";
    private final ULHService ulhService;

    @Autowired
    public GeoEnabledFilter(ULHService ulhService) {
        this.ulhService = ulhService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (SecurityContextHolder.getContext().getAuthentication() != null && session.getAttribute(GEO_FILTERED) == null) {
            AuthenticationHistory.Status status = ulhService.processUserLocation(request, HttpUtil.getClientIP(request));
            if (status.isBlocked()) {
                response.addCookie(new Cookie(OtpAuthController.RECOVER_REDIRECT, ""));
                response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
                response.sendRedirect(HttpUtil.getBaseUrl(request).concat(OtpAuthController.RECOVER));
                //response.setHeader(HttpHeaders.LOCATION, response.encodeRedirectURL(HttpUtil.getBaseUrl(request).concat(OtpAuthController.RECOVER)));
                return;
            } else {
                session.setAttribute(GEO_FILTERED, true);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestedUri = getRequestedUri(request);
        Set<String> _whitelisted = new HashSet<>(OtpAuthController.PAGE_RESOURCES);
        _whitelisted.addAll(AuthControllerAdvice.PAGES_RESOURCES);
        return super.shouldNotFilter(request) || requestedUri.equals(HttpUtil.getBaseUrl(request).concat(OtpAuthController.RECOVER)) || _whitelisted.stream().anyMatch(requestedUri::endsWith);
    }
}
