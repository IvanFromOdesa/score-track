package com.teamk.scoretrack.module.core.entities.sport_api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;
import com.teamk.scoretrack.module.core.entities.sport_type.SportType;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties({"default", "lookup", "key"})
public enum SportAPI implements IEnumConvert<Integer, SportAPI> {
    API_NBA(0, "NBA-API", new int[]{SportType.BASKETBALL.getKey()}, "/nbaapi"),
    UNDEFINED(CODE_UNDEFINED, "", null, "");
    public static final BiMap<Integer, SportAPI> LOOKUP_MAP = EnumUtils.createLookup(SportAPI.class);

    private final int code;
    private final String name;
    private final String logoUrl;
    /**
     * Codes of sport types.
     */
    private final int[] sportTypes;
    private final String basePath;

    SportAPI(int code, String name, int[] sportTypes, String basePath) {
        this.code = code;
        this.name = name;
        this.logoUrl = (name == null || name.isEmpty()) ? "" : "/api-logos/" + name + ".min.png";
        this.sportTypes = sportTypes;
        this.basePath = basePath;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public int[] getSportTypes() {
        return sportTypes;
    }

    public String getBasePath() {
        return basePath;
    }

    public static List<SportAPI> supported() {
        return List.of(API_NBA);
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
