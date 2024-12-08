package com.teamk.scoretrack.module.core.entities.user.base.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.teamk.scoretrack.module.commons.io.CustomTypeSerializer;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;

import java.io.IOException;
import java.time.Instant;

public abstract class UserSerializer<U extends User> extends CustomTypeSerializer<U> {
    protected UserSerializer(Class<U> t) {
        super(t);
    }

    @Override
    protected void writeToJson(U u, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStringField("id", u.getId().toString());
        jsonGenerator.writeStringField("preferredLang", u.getPreferredLang().getAlias());
        Instant lastSeen = u.getLastSeen();
        if (lastSeen != null) {
            jsonGenerator.writeNumberField("lastSeen", lastSeen.toEpochMilli());
        }
    }
}
