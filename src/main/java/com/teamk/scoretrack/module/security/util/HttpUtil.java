package com.teamk.scoretrack.module.security.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public final class HttpUtil {
    public static String getBaseUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
    }

    public static String getClientIP(HttpServletRequest request) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
