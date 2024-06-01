package com.teamk.scoretrack.module.core.api.nbaapi.commons.config;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class APINbaLocaleConfiguration {
    private static final String API_NBA = "nbaapi";
    public static final String SPORT_COMPONENTS = API_NBA + "/sport-components";
    public static final String INFO_HELPER = API_NBA + "/info-helper";

    @Bean(SPORT_COMPONENTS)
    @Scope("prototype")
    public MessageSource sportComponentsBundles() {
        return LocaleConfiguration.getRB(SPORT_COMPONENTS);
    }

    @Bean(INFO_HELPER)
    @Scope("prototype")
    public MessageSource infoHelperBundles() {
        return LocaleConfiguration.getRB(INFO_HELPER);
    }
}
