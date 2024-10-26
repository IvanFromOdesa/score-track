package com.teamk.scoretrack.module.commons.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

public class StandardLocalDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (localDate == null) {
            jsonGenerator.writeNull();
        } else {
            Locale locale = LocaleContextHolder.getLocale();
            String date = Language.byLocale(locale).getDateFormatter().format(localDate);
            jsonGenerator.writeString(date);
        }
    }
}
