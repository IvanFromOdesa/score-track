package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.projection.AvgStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerGamePosition;

/**
 * Position is the most common position for a given player for games in a given season.
 */
public class PlayerAvgStats extends AvgStats {
    private PlayerGamePosition position;

    public PlayerGamePosition getPosition() {
        return position;
    }

    public void setPosition(PlayerGamePosition position) {
        this.position = position;
    }
}
