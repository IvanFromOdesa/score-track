package com.teamk.scoretrack.module.commons.other;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Locale;

// TODO: rewrite this
@Component
public class ScoreTrackConfig {
    public static final boolean IS_REQUEST_SCOPE = RequestContextHolder.getRequestAttributes() != null;
    public static final Locale CURRENT_LOCALE = LocaleContextHolder.getLocale();
    private @Value("${spring.application.name}") String appName;
    private @Value("${application.locale}") String defaultLocaleCode;

    public String getAppName() {
        return appName;
    }

    public String getDefaultLocaleCode() {
        return defaultLocaleCode;
    }

    public Locale getAppLocale() {
        return new Locale(defaultLocaleCode);
    }
}
