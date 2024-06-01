package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaResponseDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APINbaPlayersStatsDto extends APINbaResponseDto<APINbaPlayerStatsDto> {
}
