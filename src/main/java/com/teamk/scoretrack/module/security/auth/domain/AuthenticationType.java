package com.teamk.scoretrack.module.security.auth.domain;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

public enum AuthenticationType implements IEnumConvert<Integer, AuthenticationType> {
    EMAIL(0), PHONE_NUMBER(1), MANUAL(2), UNDEFINED(CODE_UNDEFINED);

    public static final BiMap<Integer, AuthenticationType> LOOKUP_MAP = EnumUtils.createLookup(AuthenticationType.class);
    private final int code;

    AuthenticationType(int code) {
        this.code = code;
    }

    @Override
    public BiMap<Integer, AuthenticationType> getLookup() {
        return LOOKUP_MAP;
    }

    @Override
    public AuthenticationType getDefault() {
        return UNDEFINED;
    }

    @Override
    public Integer getKey() {
        return code;
    }
}
