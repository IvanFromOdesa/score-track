package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

import java.util.Map;

public record APINbaTeamStatsMapDto(Map<Integer, APINbaTeamStatsDto> data, Integer[] seasons) {
}
