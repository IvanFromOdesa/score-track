package com.teamk.scoretrack.module.security.token.otp.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RecoverRequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(HttpMethod.GET.name())) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(OtpAuthController.RECOVER_REDIRECT)) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        return true;
                    }
                }
            }
            throw new AccessDeniedException("");
        }
        return true;
    }
}
