package com.teamk.scoretrack.module.core.entities.user.support.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.teamk.scoretrack.module.core.entities.user.base.io.UserSerializer;
import com.teamk.scoretrack.module.core.entities.user.support.domain.SupportUser;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SupportUserSerializer extends UserSerializer<SupportUser> {
    public SupportUserSerializer() {
        super(SupportUser.class);
    }

    @Override
    protected void writeToJson(SupportUser supportUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        super.writeToJson(supportUser, jsonGenerator, serializerProvider);
        jsonGenerator.writeStringField("role", supportUser.getRole().getRoleAlias());
    }

    @Override
    public Class<SupportUser> getSerializationClass() {
        return SupportUser.class;
    }
}
