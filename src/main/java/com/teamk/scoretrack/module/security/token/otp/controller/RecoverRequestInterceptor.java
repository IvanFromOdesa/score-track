package com.teamk.scoretrack.module.security.token.otp.controller;

import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RecoverRequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(HttpMethod.GET.name())) {
            if (request.getSession().getAttribute(OtpAuthController.RECOVER_REDIRECT) == null) {
                throw new AccessDeniedException("");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            String viewName = modelAndView.getViewName();
            if (viewName != null && viewName.endsWith(AuthenticationController.HOME)) {
                request.getSession().removeAttribute(OtpAuthController.RECOVER_REDIRECT);
            }
        }
    }
}
