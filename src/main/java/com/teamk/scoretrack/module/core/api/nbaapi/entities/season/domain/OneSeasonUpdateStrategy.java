package com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain;

import java.util.List;

public abstract class OneSeasonUpdateStrategy implements SeasonUpdateStrategy {
    @Override
    public SeasonUpdateOptions getSeasonsToUpdate(List<SupportedSeasons> alreadyUpdated) {
        SupportedSeasons ongoingSeason = SupportedSeasons.getOngoingSeason();
        if (alreadyUpdated.isEmpty()) {
            return new SeasonUpdateOptions(List.of(ongoingSeason), getRequiredRequestQuotas());
        } else {
            SupportedSeasons previousToOldest = alreadyUpdated.stream().sorted().findFirst().get().getPreviousSeason();
            List<SupportedSeasons> toUpdate = previousToOldest == null ? List.of(ongoingSeason) : List.of(previousToOldest);
            return new SeasonUpdateOptions(toUpdate, getRequiredRequestQuotas());
        }
    }

    protected abstract int getRequiredRequestQuotas();
}
