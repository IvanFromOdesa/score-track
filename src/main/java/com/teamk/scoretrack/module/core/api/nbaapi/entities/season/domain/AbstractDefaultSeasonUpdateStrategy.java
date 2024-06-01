package com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain;

import java.util.List;

/**
 * The default season update strategy:<br/>
 * If there's no previous collection update found -> update current season + current season - 1.<br/>
 * If there's previous collection update found, check available seasons -> update oldest - 1 (if found - till 2015) + current season.<br/>
 * If there's previous collection update found, check available seasons -> all seasons updated -> update current season.<br/>
 * E.g. (current season = 2023)<br/>
 * 1st update -> 2023, 2022<br/>
 * 2nd update -> 2021, 2023<br/>
 * ...<br/>
 * 8th -> 2015, 2023<br/>
 * 9th -> 2023<br/>
 */
public abstract class AbstractDefaultSeasonUpdateStrategy implements SeasonUpdateStrategy {
    @Override
    public SeasonUpdateOptions getSeasonsToUpdate(List<SupportedSeasons> alreadyUpdated) {
        SupportedSeasons ongoingSeason = SupportedSeasons.getOngoingSeason();
        if (alreadyUpdated.isEmpty()) {
            return new SeasonUpdateOptions(List.of(ongoingSeason, ongoingSeason.getPreviousSeason()), getRequiredRequestQuotas(2));
        } else {
            SupportedSeasons previousToOldest = alreadyUpdated.stream().sorted().findFirst().get().getPreviousSeason();
            List<SupportedSeasons> toUpdate = previousToOldest == null ? List.of(ongoingSeason) : List.of(previousToOldest, ongoingSeason);
            return new SeasonUpdateOptions(toUpdate, getRequiredRequestQuotas(toUpdate.size()));
        }
    }

    protected abstract int getRequiredRequestQuotas(int seasonsToUpdateCount);
}
