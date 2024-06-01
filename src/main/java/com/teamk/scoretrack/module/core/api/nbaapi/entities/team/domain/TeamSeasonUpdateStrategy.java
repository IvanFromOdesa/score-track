package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.AbstractDefaultSeasonUpdateStrategy;

public class TeamSeasonUpdateStrategy extends AbstractDefaultSeasonUpdateStrategy {
    @Override
    protected int getRequiredRequestQuotas(int seasonsToUpdateCount) {
        return seasonsToUpdateCount * 30 + 1;
    }
}
