package com.teamk.scoretrack.module.security.token.jwt.model;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public record AccessToken(String value, Instant exp) {
    /**
     * Nimbus uses GSON (instead of default Jackson) for serialization / deserialization.
     * @param name claim name
     * @param cast cast function
     * @param <S> serialization type
     * @param <C> deserialization type
     */
    public record Claims<S, C>(String name, Function<S, C> cast) {
        public static final String SCOPE_PREFIX = "scope";
        public static final Claims<String, String> EMAIL = new Claims<>("email", s -> s);
        public static final Claims<String, UUID> EXTERNAL_ID = new Claims<>("external_id", UUID::fromString);
        public static final Claims<String, Language> PREFERRED_LANGUAGE = new Claims<>("lang", Language::byAlias);

        public C extractClaim(Map<String, Object> claims, Class<S> castClass) {
            return this.cast.apply(castClass.cast(claims.get(this.name)));
        }
    }
}
