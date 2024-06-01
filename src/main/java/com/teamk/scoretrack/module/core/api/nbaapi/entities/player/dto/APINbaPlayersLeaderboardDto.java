package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.teamk.scoretrack.module.core.api.commons.base.UiHint;

import java.util.List;

public record APINbaPlayersLeaderboardDto(List<APINbaPlayerLeaderboardDto> data, UiHint hint) {
}
