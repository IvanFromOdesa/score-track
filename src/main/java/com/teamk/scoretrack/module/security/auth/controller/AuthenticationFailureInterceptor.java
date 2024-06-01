package com.teamk.scoretrack.module.security.auth.controller;

import com.teamk.scoretrack.module.security.auth.exception.AuthenticationBlockedStatus;
import com.teamk.scoretrack.module.security.auth.exception.AuthenticationFailureException;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import com.teamk.scoretrack.module.security.handler.ExtendedHandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import static com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptionsUtils.getAlertDisplayOptions;

@Component
public class AuthenticationFailureInterceptor implements ExtendedHandlerInterceptor {
    private static final String SPRING_SECURITY_LAST_EXCEPTION_KEY = "SPRING_SECURITY_LAST_EXCEPTION";
    private final AuthTranslatorService translatorService;

    @Autowired
    public AuthenticationFailureInterceptor(AuthTranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        HttpSession session = request.getSession();
        Exception e = (Exception) session.getAttribute(SPRING_SECURITY_LAST_EXCEPTION_KEY);
        if (e != null) {
            this.handleExceptionForModelView(modelAndView, e);
            session.removeAttribute(SPRING_SECURITY_LAST_EXCEPTION_KEY);
        } else {
            getAlertDisplayOptions(session).ifPresent(o -> {
                if (o.isAccountActivated()) {
                    modelAndView.addObject("alertInfo", translatorService.getMessage("auth.account.enabled"));
                    o.setAccountActivated(false);
                }
            });
        }
    }

    private void handleExceptionForModelView(ModelAndView modelAndView, Exception e) {
        String key = "error";
        if (e instanceof AuthenticationFailureException) {
            modelAndView.addObject(key, translatorService.getMessage(((AuthenticationFailureException) e).getBlockStatus().getCode()));
        } else if (e instanceof DisabledException) {
            modelAndView.addObject("alertInfo", translatorService.getMessage("auth.account.activate"));
        } else {
            modelAndView.addObject(key, translatorService.getMessage(AuthenticationBlockedStatus.DEFAULT.getCode()));
        }
    }

    @Override
    public String[] getPathPatterns() {
        return new String[] { AuthenticationController.LOGIN.concat("/**") };
    }
}
