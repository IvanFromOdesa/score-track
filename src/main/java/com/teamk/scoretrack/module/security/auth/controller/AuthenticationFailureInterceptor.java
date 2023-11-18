package com.teamk.scoretrack.module.security.auth.controller;

import com.teamk.scoretrack.module.security.auth.exception.AuthenticationBlockedStatus;
import com.teamk.scoretrack.module.security.auth.exception.AuthenticationFailureException;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthenticationFailureInterceptor implements HandlerInterceptor {
    private static final String SPRING_SECURITY_LAST_EXCEPTION_KEY = "SPRING_SECURITY_LAST_EXCEPTION";
    private final AuthTranslatorService translatorService;

    @Autowired
    public AuthenticationFailureInterceptor(AuthTranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        Exception e = (Exception) request.getSession().getAttribute(SPRING_SECURITY_LAST_EXCEPTION_KEY);
        if (e != null) {
            String key = "error";
            if (e instanceof AuthenticationFailureException) {
                modelAndView.addObject(key, translatorService.getMessage(((AuthenticationFailureException) e).getBlockStatus().getCode()));
            } else {
                modelAndView.addObject(key, translatorService.getMessage(AuthenticationBlockedStatus.DEFAULT.getCode()));
            }
        }
    }
}
