package com.teamk.scoretrack.module.core.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties({"default", "lookup", "key"})
public enum SportType implements IEnumConvert<Integer, SportType> {
    BASKETBALL(0, "Basketball"),
    FOOTBALL(1, "Football"),
    RUGBY(2, "Rugby"),
    UNDEFINED(CODE_UNDEFINED, "");

    public static final BiMap<Integer, SportType> LOOKUP_MAP = EnumUtils.createLookup(SportType.class);
    private final int code;
    private final String name;

    SportType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static List<SportType> byName(String name) {
        return LOOKUP_MAP.values().stream().filter(s -> s.getName().toLowerCase().contains(name)).toList();
    }

    @JsonCreator
    public static SportType byCodeAndName(@JsonProperty("code") int code, @JsonProperty("name") String name) {
        SportType sportType = LOOKUP_MAP.get(code);
        return sportType != null && sportType.name.contains(name) ? sportType : UNDEFINED;
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
