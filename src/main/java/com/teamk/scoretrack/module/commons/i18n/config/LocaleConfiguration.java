package com.teamk.scoretrack.module.commons.i18n.config;

import com.teamk.scoretrack.module.commons.i18n.ExposedResourceBundleMessageSource;
import com.teamk.scoretrack.module.security.geo.filter.GeoEnabledFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * We define the language for the user thread based on the user ip in {@link GeoEnabledFilter}.
 * @apiNote beans declared below are of type 'prototype' as the concrete implementation of the
 * {@link MessageSource} - that is {@link ExposedResourceBundleMessageSource} is stateful due to
 * the usage of {@link ExposedResourceBundleMessageSource#setTargetFileName(String)} which might be
 * called in the runtime. However, all of these beans are to be injected in the singleton-scoped services
 * beans that are responsible for i18n and delegate all of it processing to the corresponding injected beans.
 * On this basis singleton-scoped beans that have prototype-scoped beans will not use new instances of the latter
 * for each and every request, <a href="https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch04s04.html">unless they are configured to do so</a>.
 */
@Configuration
public class LocaleConfiguration {
    @Value("${application.locale}")
    private String defaultLocaleCode;
    private static final String ST = "score-track";
    public static final String AUTH = ST + "/auth";
    public static final String GEO = ST + "/lh";
    public static final String STATUS = ST + "/status";
    public static final String OTP = ST + "/otp";

    @Bean(AUTH)
    @Scope("prototype")
    public MessageSource authBundles() {
        return getRB(AUTH);
    }

    @Bean(GEO)
    @Scope("prototype")
    public MessageSource geoBundles() {
        return getRB(GEO);
    }

    @Bean(STATUS)
    @Scope("prototype")
    public MessageSource statusBundles() {
        return getRB(STATUS);
    }

    @Bean(OTP)
    @Scope("prototype")
    public MessageSource recoverBundles() {
        return getRB(OTP);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(defaultLocaleCode));
        return localeResolver;
    }

    private ExposedResourceBundleMessageSource getRB(String path) {
        ExposedResourceBundleMessageSource messageSource = new ExposedResourceBundleMessageSource();
        messageSource.setBasename(String.format("classpath*:/bundle/%s/*", path));
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(10);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    /*@Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }*/
}
