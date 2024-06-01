package com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto;

import com.teamk.scoretrack.module.core.api.commons.base.UiWrapperResponse;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;

public final class SupportedSeasonsDto extends UiWrapperResponse {
    private SupportedSeasonsDto(String value, String uiText) {
        super(value, uiText);
    }

    public static SupportedSeasonsDto fromSeason(SupportedSeasons season) {
        return new SupportedSeasonsDto(String.valueOf(season.getYear()), season.getUiText());
    }
}
