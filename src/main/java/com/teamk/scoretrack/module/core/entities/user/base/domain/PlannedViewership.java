package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;
import com.teamk.scoretrack.module.core.entities.SportAPI;

import java.util.concurrent.TimeUnit;

public enum PlannedViewership implements IEnumConvert<Integer, PlannedViewership> {
    PLAN_API_STANDARD(23, new int[] {SportAPI.API_NBA.getKey()}, TimeUnit.DAYS.toMillis(60)),
    RESELECTED(999, null, 0),
    UNDEFINED(-1, null, 0);

    public static final BiMap<Integer, PlannedViewership> LOOKUP_MAP = EnumUtils.createLookup(PlannedViewership.class);

    private final int code;
    private final int[] apiCodes;
    private final long duration;

    PlannedViewership(int code, int[] apiCodes, long duration) {
        this.code = code;
        this.apiCodes = apiCodes;
        this.duration = duration;
    }

    public int getCode() {
        return code;
    }

    public int[] getApiCodes() {
        return apiCodes;
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
