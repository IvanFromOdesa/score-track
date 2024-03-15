package com.teamk.scoretrack.module.core.entities.user.client.convert;

import com.teamk.scoretrack.module.commons.util.enums.convert.EnumCustomFieldToPersistConverter;
import com.teamk.scoretrack.module.core.entities.user.client.domain.PlannedViewership;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PlannedViewershipCodeToPersistConverter extends EnumCustomFieldToPersistConverter<Integer, PlannedViewership, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PlannedViewership plannedViewership) {
        return byValue(plannedViewership);
    }

    @Override
    public PlannedViewership convertToEntityAttribute(Integer code) {
        return byKey(PlannedViewership.UNDEFINED, code);
    }
}
