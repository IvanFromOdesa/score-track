package com.teamk.scoretrack.module.security.auth.domain;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

public enum AuthenticationStatus implements IEnumConvert<Integer, AuthenticationStatus> {
    CREATED(0),
    ACTIVATED(1),
    BLOCKED(2);

    public static final BiMap<Integer, AuthenticationStatus> LOOKUP_MAP = EnumUtils.createLookup(AuthenticationStatus.class);

    private final int code;

    AuthenticationStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isActivated() {
        return this == ACTIVATED;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }

    @Override
    public BiMap<Integer, AuthenticationStatus> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public AuthenticationStatus getDefault() {
        return CREATED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
