package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaResponseDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APINbaTeamsStatsDto extends APINbaResponseDto<APINbaTeamStatsDto> {
}
