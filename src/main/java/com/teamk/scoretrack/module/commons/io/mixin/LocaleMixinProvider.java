package com.teamk.scoretrack.module.commons.io.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleMixinProvider implements IMixinAware<Locale> {
    @Override
    public Class<Locale> getTargetClass() {
        return Locale.class;
    }

    @Override
    public Class<?> getMixinClass() {
        return LocaleMixin.class;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    public abstract static class LocaleMixin {
        @JsonCreator
        public LocaleMixin(@JsonProperty("locale") String locale) {
        }
    }
}
