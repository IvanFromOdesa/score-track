package com.teamk.scoretrack.module.commons.io.mixin;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public interface IMixinAware<T> {
    Class<T> getTargetClass();

    default Class<?> getMixinClass() {
        return AllowDeserializationMixin.class;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    abstract class AllowDeserializationMixin {}
}
