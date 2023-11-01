package com.teamk.scoretrack.module.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class BaseSecurityFilter extends OncePerRequestFilter {
    protected String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        /*if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];*/
        /*return "128.101.101.101";*/ // United States
        // return "41.238.0.198"; // Egypt
        return "104.253.57.0"; // Ukraine
    }

    protected String getRequestedUri(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequestUri(request).build().toUriString();
    }
}
