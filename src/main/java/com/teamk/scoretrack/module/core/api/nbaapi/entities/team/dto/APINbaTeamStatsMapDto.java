package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

import com.teamk.scoretrack.module.core.api.commons.base.UiHint;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.NbaTeamInfoHelper;

import java.util.Map;

public record APINbaTeamStatsMapDto(
        Map<String, APINbaTeamStatsDto> data,
        SupportedSeasonsDto[] seasons,
        NbaTeamInfoHelper infoHelper,
        UiHint hint
        ) {
}
