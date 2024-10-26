package com.teamk.scoretrack.module.security.pwdreset.controller;

import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import com.teamk.scoretrack.module.security.handler.ExtendedHandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PwdResetInterceptor implements ExtendedHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (method.equals(HttpMethod.GET.name()) || method.equals(HttpMethod.POST.name())) {
            if (request.getSession().getAttribute(PwdResetController.PWD_RESET_CONFIRMED_URL_TOKEN) == null) {
                throw new AccessDeniedException("");
            }
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getMethod().equals(HttpMethod.POST.name())) {
            String viewName = modelAndView.getViewName();
            if (viewName != null && viewName.endsWith(AuthenticationController.LOGIN)) {
                request.getSession().removeAttribute(PwdResetController.PWD_RESET_CONFIRMED_URL_TOKEN);
            }
        }
    }

    @Override
    public String[] getPathPatterns() {
        return new String[] { PwdResetController.NEW_PWD.concat("/**") };
    }
}
