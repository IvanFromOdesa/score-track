package com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto;

import java.util.Map;

public abstract class SupportedSeasonsMapDto<T> {
    private final SupportedSeasonsDto[] seasons;
    private final Map<String, T> data;

    protected SupportedSeasonsMapDto(SupportedSeasonsDto[] seasons, Map<String, T> data) {
        this.seasons = seasons;
        this.data = data;
    }

    public SupportedSeasonsDto[] getSeasons() {
        return seasons;
    }

    public Map<String, T> getData() {
        return data;
    }
}
