package com.teamk.scoretrack.module.security.session.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class SessionTranslatorService extends TranslatorService {
    @Override
    public void setMessageSource(@Qualifier(LocaleConfiguration.SESSION) MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.SESSION;
    }
}
