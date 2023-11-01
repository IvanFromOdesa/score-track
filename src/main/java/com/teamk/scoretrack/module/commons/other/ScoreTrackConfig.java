package com.teamk.scoretrack.module.commons.other;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Locale;

@Component
public class ScoreTrackConfig {
    public static final boolean IS_REQUEST_SCOPE = RequestContextHolder.getRequestAttributes() != null;
    public static final Locale CURRENT_LOCALE = LocaleContextHolder.getLocale();
    private static Locale DEFAULT_LOCALE;
    private static String APP_NAME;
    private @Value("${spring.application.name}") String appName;
    private @Value("${application.locale}") String defaultLocaleCode;

    @PostConstruct
    protected void init() {
        DEFAULT_LOCALE = new Locale(defaultLocaleCode);
        APP_NAME = appName;
    }

    public static Locale getDefaultLocale() {
        return DEFAULT_LOCALE;
    }

    public static String getAppName() {
        return APP_NAME;
    }
}
