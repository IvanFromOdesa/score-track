package com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain;

import java.util.List;

public interface SeasonUpdateStrategy {
    SeasonUpdateOptions getSeasonsToUpdate(List<SupportedSeasons> alreadyUpdated);
}
