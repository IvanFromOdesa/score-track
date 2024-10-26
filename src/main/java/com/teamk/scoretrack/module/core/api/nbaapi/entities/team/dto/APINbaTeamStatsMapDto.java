package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsMapDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.NbaTeamInfoHelper;

import java.util.Map;

public class APINbaTeamStatsMapDto extends SupportedSeasonsMapDto<APINbaTeamStatsDto> {
    private final NbaTeamInfoHelper infoHelper;
    private final String description;

    public APINbaTeamStatsMapDto(SupportedSeasonsDto[] seasons,
                                 Map<String, APINbaTeamStatsDto> data,
                                 NbaTeamInfoHelper infoHelper,
                                 String description) {
        super(seasons, data);
        this.infoHelper = infoHelper;
        this.description = description;
    }

    public NbaTeamInfoHelper getInfoHelper() {
        return infoHelper;
    }

    public String getDescription() {
        return description;
    }
}
