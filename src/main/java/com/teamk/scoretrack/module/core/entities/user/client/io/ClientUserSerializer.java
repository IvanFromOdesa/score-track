package com.teamk.scoretrack.module.core.entities.user.client.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.teamk.scoretrack.module.core.entities.user.base.io.UserSerializer;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;

import java.io.IOException;

public abstract class ClientUserSerializer<C extends ClientUser> extends UserSerializer<C> {
    protected ClientUserSerializer(Class<C> cClass) {
        super(cClass);
    }

    @Override
    protected void writeToJson(C clientUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        super.writeToJson(clientUser, jsonGenerator, serializerProvider);
        // Profile is lazy, we don't serialize it
        if (clientUser.getViewershipPlan() != null) {
            jsonGenerator.writeObjectField("viewershipPlan", clientUser.getViewershipPlan());
        }
    }
}
