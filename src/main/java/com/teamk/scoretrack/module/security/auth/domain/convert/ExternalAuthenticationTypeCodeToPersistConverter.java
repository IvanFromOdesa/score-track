package com.teamk.scoretrack.module.security.auth.domain.convert;

import com.teamk.scoretrack.module.commons.util.enums.convert.EnumCustomFieldToPersistConverter;
import com.teamk.scoretrack.module.security.oauth2.external.ExternalAuthentication;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ExternalAuthenticationTypeCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, ExternalAuthentication.Type, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ExternalAuthentication.Type type) {
        return byValue(type);
    }

    @Override
    public ExternalAuthentication.Type convertToEntityAttribute(Integer code) {
        return byKey(ExternalAuthentication.Type.UNDEFINED, code);
    }
}
