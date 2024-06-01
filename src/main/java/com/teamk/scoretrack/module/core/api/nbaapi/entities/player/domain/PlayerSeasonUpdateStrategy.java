package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.OneSeasonUpdateStrategy;

public class PlayerSeasonUpdateStrategy extends OneSeasonUpdateStrategy {
    @Override
    protected int getRequiredRequestQuotas() {
        return 60;
    }
}
