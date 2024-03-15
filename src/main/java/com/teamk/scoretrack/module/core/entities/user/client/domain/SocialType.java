package com.teamk.scoretrack.module.core.entities.user.client.domain;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

public enum SocialType implements IEnumConvert<Integer, SocialType> {
    INSTAGRAM(0, "https://instagram.com"), X(1, "https://twitter.com"), UNDEFINED(CODE_UNDEFINED, "");

    public static final BiMap<Integer, SocialType> LOOKUP_MAP = EnumUtils.createLookup(SocialType.class);

    private final int code;
    private final String prefix;

    SocialType(int code, String prefix) {
        this.code = code;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isInstagram() {
        return this == INSTAGRAM;
    }

    public boolean isX() {
        return this == X;
    }

    @Override
    public BiMap<Integer, SocialType> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public SocialType getDefault() {
        return UNDEFINED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}