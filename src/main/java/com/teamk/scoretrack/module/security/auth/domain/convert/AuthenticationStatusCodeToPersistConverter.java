package com.teamk.scoretrack.module.security.auth.domain.convert;

import com.teamk.scoretrack.module.commons.util.enums.convert.EnumCustomFieldToPersistConverter;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationStatus;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AuthenticationStatusCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, AuthenticationStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AuthenticationStatus authenticationStatus) {
        return byValue(authenticationStatus);
    }

    @Override
    public AuthenticationStatus convertToEntityAttribute(Integer code) {
        return byKey(AuthenticationStatus.CREATED, code);
    }
}
