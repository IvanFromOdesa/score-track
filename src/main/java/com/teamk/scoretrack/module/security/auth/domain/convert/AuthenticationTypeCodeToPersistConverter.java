package com.teamk.scoretrack.module.security.auth.domain.convert;

import com.teamk.scoretrack.module.commons.util.enums.convert.EnumCustomFieldToPersistConverter;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AuthenticationTypeCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, AuthenticationType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AuthenticationType authenticationType) {
        return byValue(authenticationType);
    }

    @Override
    public AuthenticationType convertToEntityAttribute(Integer code) {
        return byKey(AuthenticationType.UNDEFINED, code);
    }
}
