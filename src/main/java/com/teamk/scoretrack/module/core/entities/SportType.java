package com.teamk.scoretrack.module.core.entities;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

public enum SportType implements IEnumConvert<Integer, SportType> {
    BASKETBALL(0, "basketballKey"),
    FOOTBALL(1, "footballKey"),
    RUGBY(2, "rugbyKey"),
    UNDEFINED(-1, "");

    public static final BiMap<Integer, SportType> LOOKUP_MAP = EnumUtils.createLookup(SportType.class);
    private final int code;
    private final String nameKey;

    SportType(int code, String nameKey) {
        this.code = code;
        this.nameKey = nameKey;
    }

    public String getNameKey() {
        return nameKey;
    }

    @Override
    public BiMap<Integer, SportType> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public SportType getDefault() {
        return UNDEFINED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
