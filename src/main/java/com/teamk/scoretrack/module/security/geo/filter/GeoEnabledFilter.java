package com.teamk.scoretrack.module.security.geo.filter;

import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import com.teamk.scoretrack.module.security.geo.service.ULHService;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
            ulhService.processUserLocation(request, response, getClientIP(request));
            session.setAttribute(GEO_FILTERED, true);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !getRequestedUri(request).equals(HttpUtil.getBaseUrl(request).concat(AuthenticationController.HOME));
    }
}
