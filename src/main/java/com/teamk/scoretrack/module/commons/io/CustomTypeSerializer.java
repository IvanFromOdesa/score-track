package com.teamk.scoretrack.module.commons.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public abstract class CustomTypeSerializer<S> extends StdSerializer<S> implements ISerializationClassAware<S> {
    protected CustomTypeSerializer(Class<S> s) {
        super(s);
    }

    @Override
    public void serializeWithType(S s, JsonGenerator gen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefix(gen, typeSer.typeId(s, JsonToken.START_OBJECT));
        this.writeToJson(s, gen, provider);
        typeSer.writeTypeSuffix(gen, typeSer.typeId(s, JsonToken.END_OBJECT));
    }

    @Override
    public void serialize(S s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        this.writeToJson(s, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndObject();
    }

    protected abstract void writeToJson(S s, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException;
}
