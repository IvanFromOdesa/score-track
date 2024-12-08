package com.teamk.scoretrack.module.commons.i18n.service;

import com.teamk.scoretrack.module.commons.i18n.ExposedResourceBundleMessageSource;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Designed to work specifically with {@link ExposedResourceBundleMessageSource}
 */
public abstract class TranslatorService implements MessageSourceResolver {
    protected MessageSource messageSource;

    public String getMessage(String code) {
        return getMessage(code, LocaleContextHolder.getLocale(), (Object) null);
    }

    public String getMessage(String code, Locale locale) {
        return getMessage(code, locale, (Object) null);
    }

    public String getMessage(String code, Object... args) {
        return getMessage(code, LocaleContextHolder.getLocale(), args);
    }

    // Args is objects to be placed into text placeholders
    public String getMessage(String code, Locale locale, Object... args) {
        return getMessageSourceBean().getMessage(code, args, locale);
    }

    public Map<String, String> getMessages() {
        return getMessages(LocaleContextHolder.getLocale());
    }

    /**
     * Useful when a dir with a bunch of bundles is specified in config, and we want to retrieve only specific bundle
     * @param
     * @return
     */
    public Map<String, String> getMessages(String bundleName) {
        return getMessages(bundleName, LocaleContextHolder.getLocale());
    }

    public Map<String, String> getMessages(String bundleName, Locale locale) {
        ExposedResourceBundleMessageSource messageSourceBean = getMessageSourceBean();
        if (bundleName != null) {
            messageSourceBean.setTargetFileName(bundleName);
        }
        return getMessages(locale, messageSourceBean);
    }

    public Map<String, String> getMessages(Locale locale) {
        return getMessages(locale, getMessageSourceBean());
    }

    public Map<String, String> getMessages(Locale locale, ExposedResourceBundleMessageSource messageSourceBean) {
        Properties properties = messageSourceBean.getMessages(locale);
        return properties.entrySet().stream().filter(e -> e.getKey() != null && e.getValue() != null).collect(Collectors.toUnmodifiableMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
    }

    /**
     * @return based on the scope - new instance or singleton of {@link ExposedResourceBundleMessageSource}
     * @apiNote Beans of this type might be of type prototype as we can modify its state in runtime
     */
    private ExposedResourceBundleMessageSource getMessageSourceBean() {
        return ((AnnotationConfigServletWebServerApplicationContext) messageSource).getBean(provideBaseBundleBeanName(), ExposedResourceBundleMessageSource.class);
    }

    protected abstract String provideBaseBundleBeanName();
}
