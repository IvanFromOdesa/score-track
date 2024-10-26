package com.teamk.scoretrack.module.core.entities.sport_type;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class SportTypeTranslatorService extends TranslatorService {
    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.SPORT_TYPES;
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier(LocaleConfiguration.SPORT_TYPES) MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
