package com.teamk.scoretrack.module.security.pwdreset.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service(PwdResetTranslatorService.NAME)
public class PwdResetTranslatorService extends TranslatorService {
    public static final String NAME = "pwdResetTranslatorService";

    @Override
    @Autowired
    public void setMessageSource(@Qualifier(LocaleConfiguration.PWD_RESET) MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.PWD_RESET;
    }
}
