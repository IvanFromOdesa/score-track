package com.teamk.scoretrack.module.core.entities;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

public enum SportAPI implements IEnumConvert<Integer, SportAPI> {
    API_NBA(0, new int[]{SportType.BASKETBALL.getKey()}, "/nbaapi"),
    UNDEFINED(-1, null, "");

    public static final BiMap<Integer, SportAPI> LOOKUP_MAP = EnumUtils.createLookup(SportAPI.class);

    private final int code;
    /**
     * Codes of sport types.
     */
    private final int[] sportTypes;
    private final String basePath;

    SportAPI(int code, int[] sportTypes, String basePath) {
        this.code = code;
        this.sportTypes = sportTypes;
        this.basePath = basePath;
    }

    public int[] getSportTypes() {
        return sportTypes;
    }

    public String getBasePath() {
        return basePath;
    }

    @Override
    public BiMap<Integer, SportAPI> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public SportAPI getDefault() {
        return UNDEFINED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
