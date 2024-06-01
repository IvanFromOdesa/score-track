package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.convert;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlayerDataTopByTypeConverter implements Converter<String, PlayerDataStatCategoryInfoHelper> {
    @Override
    public PlayerDataStatCategoryInfoHelper convert(String source) {
        return PlayerDataStatCategoryInfoHelper.byFieldName(source);
    }
}
