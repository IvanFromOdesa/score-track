package com.teamk.scoretrack.module.security.session.io;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationExceptionMixinProvider implements IMixinAware<AuthenticationException> {
    @Override
    public Class<AuthenticationException> getTargetClass() {
        return AuthenticationException.class;
    }

    @Override
    public Class<?> getMixinClass() {
        return AuthenticationExceptionMixin.class;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
    @JsonIgnoreProperties(
            ignoreUnknown = true,
            value = {"cause", "stackTrace", "suppressed"}
    )
    public abstract static class AuthenticationExceptionMixin {
        @JsonCreator
        AuthenticationExceptionMixin(@JsonProperty("message") String message) {
        }
    }
}
