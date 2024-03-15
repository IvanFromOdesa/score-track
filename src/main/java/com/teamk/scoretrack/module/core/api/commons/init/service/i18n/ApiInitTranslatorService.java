package com.teamk.scoretrack.module.core.api.commons.init.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ApiInitTranslatorService extends TranslatorService {
    @Override
    @Autowired
    public void setMessageSource(@Qualifier(LocaleConfiguration.API_INIT) MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.API_INIT;
    }
}
