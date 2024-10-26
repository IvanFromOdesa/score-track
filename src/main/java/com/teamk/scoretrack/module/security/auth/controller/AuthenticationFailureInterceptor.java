package com.teamk.scoretrack.module.security.auth.controller;

import com.teamk.scoretrack.module.commons.layout.alert.UiAlertType;
import com.teamk.scoretrack.module.security.auth.exception.AuthenticationBlockedStatus;
import com.teamk.scoretrack.module.security.auth.exception.AuthenticationFailureException;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import com.teamk.scoretrack.module.security.handler.ExtendedHandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.function.Consumer;

import static com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptionsUtils.getAlertDisplayOptions;

@Component
public class AuthenticationFailureInterceptor implements ExtendedHandlerInterceptor {
    private static final String SPRING_SECURITY_LAST_EXCEPTION_KEY = "SPRING_SECURITY_LAST_EXCEPTION";
    private final AuthTranslatorService translatorService;
    private final Map<Class<? extends Exception>, Consumer<ModelAndView>> EXCEPTION_ACTION_CALLBACK_MAP;

    @Autowired
    public AuthenticationFailureInterceptor(AuthTranslatorService translatorService) {
        this.translatorService = translatorService;
        Consumer<ModelAndView> credsInvalidCallback = modelAndView -> modelAndView.addObject("error", translatorService.getMessage("auth.creds.invalid"));
        EXCEPTION_ACTION_CALLBACK_MAP = Map.of(
                DisabledException.class, modelAndView -> modelAndView.addObject(UiAlertType.INFO.getName(), translatorService.getMessage("auth.account.activate")),
                CredentialsExpiredException.class, modelAndView -> modelAndView.addObject(UiAlertType.WARNING.getName(), translatorService.getMessage("auth.account.pwdreset")),
                UsernameNotFoundException.class, credsInvalidCallback,
                BadCredentialsException.class, credsInvalidCallback
        );
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
                    modelAndView.addObject(UiAlertType.INFO.getName(), translatorService.getMessage("auth.account.enabled"));
                    o.setAccountActivated(false);
                }
            });
        }
    }

    private void handleExceptionForModelView(ModelAndView modelAndView, Exception e) {
        String key = "error";
        if (e instanceof AuthenticationFailureException) {
            modelAndView.addObject(key, translatorService.getMessage(((AuthenticationFailureException) e).getBlockStatus().getCode()));
        } else {
            EXCEPTION_ACTION_CALLBACK_MAP.getOrDefault(
                    e.getClass(),
                    mv -> modelAndView.addObject(key, translatorService.getMessage(AuthenticationBlockedStatus.DEFAULT.getCode()))
            ).accept(modelAndView);
        }
    }

    @Override
    public String[] getPathPatterns() {
        return new String[] { AuthenticationController.LOGIN.concat("/**") };
    }
}
