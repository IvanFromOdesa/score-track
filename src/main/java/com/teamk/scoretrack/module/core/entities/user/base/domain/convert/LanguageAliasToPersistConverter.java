package com.teamk.scoretrack.module.core.entities.user.base.domain.convert;

import com.teamk.scoretrack.module.commons.util.enums.convert.EnumCustomFieldToPersistConverter;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LanguageAliasToPersistConverter extends EnumCustomFieldToPersistConverter<String, Language, String> {
    @Override
    public String convertToDatabaseColumn(Language language) {
        return byValue(language);
    }

    @Override
    public Language convertToEntityAttribute(String s) {
        return byKey(Language.UNDEFINED, s);
    }
}
