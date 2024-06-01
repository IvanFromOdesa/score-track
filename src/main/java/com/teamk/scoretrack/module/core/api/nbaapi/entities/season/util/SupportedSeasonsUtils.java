package com.teamk.scoretrack.module.core.api.nbaapi.entities.season.util;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsDto;

import java.util.Collection;
import java.util.Comparator;

public class SupportedSeasonsUtils {

    public static SupportedSeasonsDto[] transformToArrayDto(Collection<SupportedSeasons> collection) {
        return collection.stream().sorted(new SupportedSeasonsByYearComparator()).map(SupportedSeasonsDto::fromSeason).toArray(SupportedSeasonsDto[]::new);
    }

    private static class SupportedSeasonsByYearComparator implements Comparator<SupportedSeasons> {
        @Override
        public int compare(SupportedSeasons o1, SupportedSeasons o2) {
            return Integer.compare(o2.getYear(), o1.getYear());
        }
    }
}
