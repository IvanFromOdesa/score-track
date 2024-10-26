package com.teamk.scoretrack.module.core.entities.sport_type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

import java.util.List;
import java.util.function.Predicate;

public enum SportType implements IEnumConvert<Integer, SportType> {
    BASKETBALL(0, "basketballKey"),
    FOOTBALL(1, "footballKey"),
    RUGBY(2, "rugbyKey"),
    UNDEFINED(CODE_UNDEFINED, "");

    public static final BiMap<Integer, SportType> LOOKUP_MAP = EnumUtils.createLookup(SportType.class);
    private final int code;
    private final String bundleKey;

    SportType(int code, String bundleKey) {
        this.code = code;
        this.bundleKey = bundleKey;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public String getBundleKey() {
        return bundleKey;
    }

    public static List<SportType> byBundleKeys(List<String> keys) {
        return getListBy(s -> keys.contains(s.getBundleKey()));
    }

    private static List<SportType> getListBy(Predicate<SportType> p) {
        return LOOKUP_MAP.values().stream().filter(p).toList();
    }

    @JsonCreator
    public static SportType byCodeAndName(@JsonProperty("code") int code,
                                          @JsonProperty("name") String name) {
        // Ignore name field
        SportType sportType = LOOKUP_MAP.get(code);
        return sportType != null ? sportType : UNDEFINED;
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
