package com.teamk.scoretrack.module.core.entities.io.convert;

import com.teamk.scoretrack.module.commons.util.enums.convert.EnumCustomFieldToPersistConverter;
import com.teamk.scoretrack.module.core.entities.io.AccessStatus;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccessStatusCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, AccessStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccessStatus accessStatus) {
        return byValue(accessStatus);
    }

    @Override
    public AccessStatus convertToEntityAttribute(Integer code) {
        return byKey(AccessStatus.UNDEFINED, code);
    }
}
