package com.teamk.scoretrack.module.commons.i18n.service;

import org.springframework.context.MessageSourceAware;

import java.util.Locale;
import java.util.Map;

public interface MessageSourceResolver extends MessageSourceAware {
    Map<String, String> getMessages(Locale locale);
    String getMessage(String key, Locale locale, Object... args);
}
