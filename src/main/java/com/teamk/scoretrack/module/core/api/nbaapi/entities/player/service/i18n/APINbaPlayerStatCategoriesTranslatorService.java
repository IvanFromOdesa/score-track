package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.config.APINbaLocaleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class APINbaPlayerStatCategoriesTranslatorService extends TranslatorService {
    public String getMessage(String code, String bundleName) {
        return getMessages(bundleName).get(code);
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return APINbaLocaleConfiguration.PLAYER_STAT_CATEGORIES;
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier(APINbaLocaleConfiguration.PLAYER_STAT_CATEGORIES) MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
