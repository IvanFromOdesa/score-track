package com.teamk.scoretrack.module.commons.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
public class LocaleDeserializer extends CustomDeserializer<Locale> {
    @Override
    public Class<Locale> getDeserializationClass() {
        return Locale.class;
    }

    @Override
    public Locale deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String localeString = jsonParser.getText();

        if (localeString != null && !localeString.isEmpty()) {
            String[] localeParts = localeString.split("_");
            if (localeParts.length == 2) {
                return new Locale(localeParts[0], localeParts[1]);
            } else if (localeParts.length == 1) {
                return new Locale(localeParts[0]);
            }
        }

        return Locale.getDefault();
    }
}

