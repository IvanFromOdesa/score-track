package com.teamk.scoretrack.module.security.auth.domain.convert;

import com.teamk.scoretrack.module.commons.util.enums.convert.EnumCustomFieldToPersistConverter;
import com.teamk.scoretrack.module.security.auth.domain.PasswordStatus;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PasswordStatusCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, PasswordStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PasswordStatus passwordStatus) {
        return byValue(passwordStatus);
    }

    @Override
    public PasswordStatus convertToEntityAttribute(Integer code) {
        return byKey(PasswordStatus.UNDEFINED, code);
    }
}
