package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsMapDto;

import java.util.Map;

public class APINbaPlayerAvgStatsMapDto extends SupportedSeasonsMapDto<APINbaPlayerAvgStatsDto> {
    private final Map<String, APINbaStatCategoriesGroupDto> groupedStatCategories;

    public APINbaPlayerAvgStatsMapDto(SupportedSeasonsDto[] seasons, Map<String, APINbaPlayerAvgStatsDto> data,
                                      Map<String, APINbaStatCategoriesGroupDto> groupedStatCategories) {
        super(seasons, data);
        this.groupedStatCategories = groupedStatCategories;
    }

    public Map<String, APINbaStatCategoriesGroupDto> getGroupedStatCategories() {
        return groupedStatCategories;
    }
}
