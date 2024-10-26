package com.teamk.scoretrack.module.security.auth.controller;

import com.teamk.scoretrack.module.commons.layout.alert.UiAlertType;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import com.teamk.scoretrack.module.security.handler.ExtendedHandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import static com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptionsUtils.getAlertDisplayOptions;

@Component
public class HomePageInterceptor implements ExtendedHandlerInterceptor {
    private final AuthTranslatorService translatorService;
    private final AuthenticationHolderService authenticationHolderService;

    @Autowired
    public HomePageInterceptor(AuthTranslatorService translatorService,
                               AuthenticationHolderService authenticationHolderService) {
        this.translatorService = translatorService;
        this.authenticationHolderService = authenticationHolderService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        authenticationHolderService.getCurrentAuthentication().ifPresent(a -> getAlertDisplayOptions(request.getSession()).ifPresent(o -> {
            if (o.isFirstLogIn()) {
                modelAndView.addObject(UiAlertType.INFO.getName(), translatorService.getMessage("auth.firstLogin", a.getLoginname()));
                o.setFirstLogIn(false);
            }
        }));
    }

    @Override
    public String[] getPathPatterns() {
        return new String[] { AuthenticationController.HOME.concat("/**") };
    }
}
