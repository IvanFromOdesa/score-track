package com.teamk.scoretrack.module.core.entities.user.client.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ProfilePageTranslatorService extends TranslatorService {
    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.PROFILE_PAGE;
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier(LocaleConfiguration.PROFILE_PAGE) MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
