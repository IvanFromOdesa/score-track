package com.teamk.scoretrack.module.core.api.nbaapi.entities.season;

import java.time.Instant;
import java.time.Month;
import java.time.Year;
import java.time.ZoneOffset;

public enum SupportedSeasons {
    _2015(2015),
    _2016(2016),
    _2017(2017),
    _2018(2018),
    _2019(2019),
    _2020(2020),
    _2021(2021),
    _2022(2022),
    _2023(2023),
    CURRENT(Year.now().getValue());

    private final int year;

    SupportedSeasons(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public static int getOngoingSeason() {
        int current = CURRENT.year;
        // Nba seasons usually start at late October of the current year
        return Month.from(Instant.now().atZone(ZoneOffset.UTC)).getValue() == Month.NOVEMBER.getValue() ? current : current - 1;
    }
}
