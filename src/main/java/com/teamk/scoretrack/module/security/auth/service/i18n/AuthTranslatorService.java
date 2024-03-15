package com.teamk.scoretrack.module.security.auth.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service(AuthTranslatorService.NAME)
public class AuthTranslatorService extends TranslatorService {
    public static final String NAME = "authTranslatorService";
    @Override
    @Autowired
    public void setMessageSource(@Qualifier(LocaleConfiguration.AUTH) MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.AUTH;
    }
}
