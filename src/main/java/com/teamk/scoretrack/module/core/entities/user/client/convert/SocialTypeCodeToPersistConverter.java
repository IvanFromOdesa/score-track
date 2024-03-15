package com.teamk.scoretrack.module.core.entities.user.client.convert;

import com.teamk.scoretrack.module.commons.util.enums.convert.EnumCustomFieldToPersistConverter;
import com.teamk.scoretrack.module.core.entities.user.client.domain.SocialType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SocialTypeCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, SocialType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SocialType socialType) {
        return byValue(socialType);
    }

    @Override
    public SocialType convertToEntityAttribute(Integer code) {
        return byKey(SocialType.UNDEFINED, code);
    }
}
