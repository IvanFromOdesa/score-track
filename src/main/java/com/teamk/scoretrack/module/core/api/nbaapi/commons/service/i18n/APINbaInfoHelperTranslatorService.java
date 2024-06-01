package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.config.APINbaLocaleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class APINbaInfoHelperTranslatorService extends TranslatorService {
    public String getMessage(String code, String bundleName) {
        return getMessages(bundleName).get(code);
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return APINbaLocaleConfiguration.INFO_HELPER;
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier(APINbaLocaleConfiguration.INFO_HELPER) MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
