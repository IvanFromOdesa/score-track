package com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain;

import java.util.List;

public record SeasonUpdateOptions(List<SupportedSeasons> seasonsToUpdate, int requiredRequestQuotas) {
}
