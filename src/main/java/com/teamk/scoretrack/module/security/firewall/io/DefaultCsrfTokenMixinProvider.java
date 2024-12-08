package com.teamk.scoretrack.module.security.firewall.io;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

@Component
public class DefaultCsrfTokenMixinProvider implements IMixinAware<DefaultCsrfToken> {
    @Override
    public Class<DefaultCsrfToken> getTargetClass() {
        return DefaultCsrfToken.class;
    }

    @Override
    public Class<?> getMixinClass() {
        return DefaultCsrfTokenMixin.class;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    public abstract static class DefaultCsrfTokenMixin {
        @JsonCreator
        public DefaultCsrfTokenMixin(
                @JsonProperty("headerName") String headerName,
                @JsonProperty("parameterName") String parameterName,
                @JsonProperty("token") String token) {
        }
    }
}
