package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.config.APINbaLocaleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class APINbaPlayerTranslatorService extends TranslatorService {
    @Override
    protected String provideBaseBundleBeanName() {
        return APINbaLocaleConfiguration.PLAYER;
    }

    @Override
    @Autowired
    public void setMessageSource(@Qualifier(APINbaLocaleConfiguration.PLAYER) MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
