package com.teamk.scoretrack.module.core.entities.sport_type;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.teamk.scoretrack.module.commons.i18n.ExposedResourceBundleMessageSource;
import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class SportTypeNameReverseTranslationIndex {
    private final ExposedResourceBundleMessageSource messageSource;
    private final Map<String, String> textToCodeIndex = new ConcurrentHashMap<>();

    @Autowired
    public SportTypeNameReverseTranslationIndex(@Qualifier(LocaleConfiguration.SPORT_TYPES) MessageSource messageSource) {
        this.messageSource = (ExposedResourceBundleMessageSource) messageSource;
    }

    @PostConstruct
    public void initialize() {
        for (Language language : Language.supported()) {
            Locale locale = language.getLocale();
            Properties properties = messageSource.getMessages(locale);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String code = entry.getKey().toString();
                String text = entry.getValue().toString();
                textToCodeIndex.put(text, code);
            }
        }
    }

    public String getCodeByExactText(String text) {
        return textToCodeIndex.get(text);
    }

    public BiMap<String, String> getCodesByText(String text) {
        return textToCodeIndex.entrySet().stream()
                .filter(e -> e.getKey().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashBiMap::create
                ));
    }
}
