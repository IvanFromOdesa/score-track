package com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain;

import java.time.Instant;
import java.time.Month;
import java.time.Year;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public enum SupportedSeasons {
    _2015(2015, "2015-16"),
    _2016(2016, "2016-17"),
    _2017(2017, "2017-18"),
    _2018(2018, "2018-19"),
    _2019(2019, "2019-20"),
    _2020(2020, "2020-21"),
    _2021(2021, "2021-22"),
    _2022(2022, "2022-23"),
    _2023(2023, "2023-24"),
    /**
     * This is a fallback, which should not be ever used
     */
    CURRENT(Years.CURRENT_YEAR, Years.CURRENT_YEAR_UI_TEXT);

    private final int year;
    private final String uiText;

    private static final Map<Integer, SupportedSeasons> LOOKUP_MAP = new TreeMap<>();

    static {
        Arrays.stream(SupportedSeasons.values()).forEach(s -> LOOKUP_MAP.put(s.year, s));
    }

    SupportedSeasons(int year, String uiText) {
        this.year = year;
        this.uiText = uiText;
    }

    public int getYear() {
        return year;
    }

    public String getUiText() {
        return uiText;
    }

    public static SupportedSeasons getOngoingSeason() {
        // Nba seasons usually start at late October of the current year
        return Month.from(Instant.now().atZone(ZoneOffset.UTC)).getValue() == Month.NOVEMBER.getValue() ? CURRENT : LOOKUP_MAP.get(CURRENT.year - 1);
    }

    public SupportedSeasons getPreviousSeason() {
        return LOOKUP_MAP.get(this.year - 1);
    }

    static class Years {
        public static final int CURRENT_YEAR = Year.now().getValue();
        public static final String CURRENT_YEAR_UI_TEXT = String.valueOf(CURRENT_YEAR).concat("-").concat(String.valueOf((CURRENT_YEAR + 1) % 100));
    }
}
