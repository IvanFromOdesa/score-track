package com.teamk.scoretrack.module.security.auth.domain;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

public enum PasswordStatus implements IEnumConvert<Integer, PasswordStatus> {
    NEW(0), RESET(1), CHANGED(2), UNDEFINED(-1);

    public static final BiMap<Integer, PasswordStatus> LOOKUP_MAP = EnumUtils.createLookup(PasswordStatus.class);
    private final int code;
    PasswordStatus(int code) {
        this.code = code;
    }

    @Override
    public BiMap<Integer, PasswordStatus> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public PasswordStatus getDefault() {
        return UNDEFINED;
    }

    @Override
    public Integer getKey() {
        return code;
    }

    public boolean isReset() {
        return this == RESET;
    }
}
