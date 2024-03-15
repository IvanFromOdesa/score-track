package com.teamk.scoretrack.module.security.recaptcha.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service(RecaptchaTranslatorService.NAME)
public class RecaptchaTranslatorService extends TranslatorService {
    public static final String NAME = "recaptchaTranslatorService";

    @Override
    @Autowired
    public void setMessageSource(@Qualifier(LocaleConfiguration.RECAPTCHA) MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.RECAPTCHA;
    }
}
