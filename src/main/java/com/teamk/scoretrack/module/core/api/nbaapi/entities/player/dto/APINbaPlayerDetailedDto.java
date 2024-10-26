package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsDto;

public record APINbaPlayerDetailedDto(
        APINbaPlayerResponseDto data,
        SupportedSeasonsDto[] seasons
        ) {
}
