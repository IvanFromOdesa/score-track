package com.teamk.scoretrack.module.core.api.nbaapi.commons.config;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class APINbaLocaleConfiguration {
    private static final String API_NBA = "nbaapi";
    public static final String SPORT_COMPONENTS = API_NBA + "/sport_components";
    private static final String TEAM = API_NBA + "/team";
    public static final String TEAM_INFO_HELPER = TEAM + "/info_helper";
    public static final String PLAYER = API_NBA + "/player";
    public static final String PLAYER_STAT_CATEGORIES = PLAYER + "/stat_categories";

    @Bean(SPORT_COMPONENTS)
    @Scope("prototype")
    public MessageSource sportComponentsBundles() {
        return LocaleConfiguration.getRB(SPORT_COMPONENTS);
    }

    @Bean(TEAM_INFO_HELPER)
    @Scope("prototype")
    public MessageSource teamInfoHelperBundles() {
        return LocaleConfiguration.getRB(TEAM_INFO_HELPER);
    }

    @Bean(PLAYER)
    @Scope("prototype")
    public MessageSource playerBundles() {
        return LocaleConfiguration.getRB(PLAYER);
    }

    @Bean(PLAYER_STAT_CATEGORIES)
    @Scope("prototype")
    public MessageSource playerStatCategoriesBundles() {
        return LocaleConfiguration.getRB(PLAYER_STAT_CATEGORIES);
    }
}
