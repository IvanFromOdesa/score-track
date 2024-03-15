package com.teamk.scoretrack.module.security.geo.i18n;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service(GeoTranslatorService.NAME)
public class GeoTranslatorService extends TranslatorService {
    public static final String NAME = "geoTranslatorService";
    @Override
    @Autowired
    public void setMessageSource(@Qualifier(LocaleConfiguration.GEO) MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.GEO;
    }
}
