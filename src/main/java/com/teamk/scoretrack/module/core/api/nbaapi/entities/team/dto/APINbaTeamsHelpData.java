package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaHelpData;

import java.util.Map;

/**
 * @param data map with avg stats for each available season
 */
public record APINbaTeamsHelpData(Map<String, APINbaTeamStatsDto> data) implements APINbaHelpData {
}
