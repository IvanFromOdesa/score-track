package com.teamk.scoretrack.module.core.entities.user.client.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;
import com.teamk.scoretrack.module.core.entities.sport_api.SportAPI;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties({"default", "lookup", "key", "duration", "valid"})
public enum PlannedViewership implements IEnumConvert<Integer, PlannedViewership> {
    PLAN_API_STANDARD(23, "API STANDARD", new SportAPI[] {SportAPI.API_NBA}, TimeUnit.DAYS.toMillis(60)),
    RESELECTED(999, "RESELECTED", new SportAPI[]{}, 0),
    UNDEFINED(CODE_UNDEFINED, "", new SportAPI[]{}, 0);

    public static final BiMap<Integer, PlannedViewership> LOOKUP_MAP = EnumUtils.createLookup(PlannedViewership.class);

    private final int code;
    private final String name;
    private final SportAPI[] sportApis;
    private final long duration;

    PlannedViewership(int code, String name, SportAPI[] sportApis, long duration) {
        this.code = code;
        this.name = name;
        this.sportApis = sportApis;
        this.duration = duration;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public SportAPI[] getSportApis() {
        return sportApis;
    }

    public int[] getApiCodes() {
        return Arrays.stream(sportApis).mapToInt(SportAPI::getCode).toArray();
    }

    public long getDuration() {
        return duration;
    }

    public boolean isValid() {
        return this != UNDEFINED && this != RESELECTED;
    }

    @Override
    public BiMap<Integer, PlannedViewership> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public PlannedViewership getDefault() {
        return UNDEFINED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
