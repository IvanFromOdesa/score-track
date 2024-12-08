package com.teamk.scoretrack.module.core.entities.user.base.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.teamk.scoretrack.module.commons.io.CustomDeserializer;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;

import java.io.IOException;
import java.time.Instant;

public abstract class UserDeserializer<U extends User> extends CustomDeserializer<U> {
    @Override
    public U deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectNode node = p.getCodec().readTree(p);
        return deserializeUser(node, p, ctxt);
    }

    protected U deserializeUser(ObjectNode node, JsonParser p, DeserializationContext ctxt) {
        U user = createInstance();

        Long id = CommonsUtil.orNull(node.get("id"), JsonNode::asLong);

        Language preferredLang = CommonsUtil.orNull(
                node.get("preferredLang"),
                n -> Language.byAlias(n.asText())
        );

        Instant lastSeen = CommonsUtil.orNull(
                node.get("lastSeen"),
                n -> Instant.ofEpochSecond(n.asLong())
        );

        user.setId(id);
        user.setPreferredLang(preferredLang);
        user.setLastSeen(lastSeen);

        return user;
    }

    protected abstract U createInstance();
}
