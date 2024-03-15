package com.teamk.scoretrack.module.core.entities.io;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonIgnoreProperties({"default", "lookup", "key"})
public enum AccessStatus implements IEnumConvert<Integer, AccessStatus> {
    REQUIRES_REVIEW(0), ACCESSIBLE(1), NSFW(2), UNDEFINED(CODE_UNDEFINED);
    public static final BiMap<Integer, AccessStatus> LOOKUP_MAP = EnumUtils.createLookup(AccessStatus.class);
    private final int code;

    AccessStatus(int code) {
        this.code = code;
    }

    public boolean isRequiresReview() {
        return this == REQUIRES_REVIEW;
    }

    public boolean isAccessible() {
        return this == ACCESSIBLE;
    }

    public boolean isNsfw() {
        return this == NSFW;
    }

    public boolean isUndefined() {
        return this == UNDEFINED;
    }

    @Override
    public BiMap<Integer, AccessStatus> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public AccessStatus getDefault() {
        return REQUIRES_REVIEW;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
