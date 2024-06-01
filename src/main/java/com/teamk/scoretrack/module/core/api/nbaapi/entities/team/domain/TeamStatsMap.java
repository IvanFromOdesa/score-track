package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;

import java.util.Map;

/**
 * Wrapper that solves issue with Jackson map deserialization
 */
public record TeamStatsMap(Map<SupportedSeasons, TeamStats> teamStats) {
}
