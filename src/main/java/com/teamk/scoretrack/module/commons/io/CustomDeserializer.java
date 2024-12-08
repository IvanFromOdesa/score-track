package com.teamk.scoretrack.module.commons.io;

import com.fasterxml.jackson.databind.JsonDeserializer;

public abstract class CustomDeserializer<T> extends JsonDeserializer<T> {
    public abstract Class<T> getDeserializationClass();
}
