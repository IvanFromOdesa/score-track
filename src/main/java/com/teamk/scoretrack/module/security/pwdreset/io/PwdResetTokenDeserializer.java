package com.teamk.scoretrack.module.security.pwdreset.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.commons.io.CustomDeserializer;
import com.teamk.scoretrack.module.commons.util.ReflectUtils;
import com.teamk.scoretrack.module.security.pwdreset.domain.PwdResetToken;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This one is used as a part of session objectMapper for password reset flow.
 */
@Component
public class PwdResetTokenDeserializer extends CustomDeserializer<PwdResetToken> {
    @Override
    public Class<PwdResetToken> getDeserializationClass() {
        return PwdResetToken.class;
    }

    @Override
    public PwdResetToken deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String id = node.get("id").asText();
        String value = node.get("value").asText();
        String email = node.get("email").asText();
        Long expiration = node.get("expiration").asLong();

        PwdResetToken token = new PwdResetToken(id, value, email);

        try {
            ReflectUtils.setField(token, "expiration", expiration, PwdResetToken.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ServerException(e);
        }

        // No need for a setter, since the token is put into session only when it's already 'used'
        if (node.get("used").asBoolean()) {
            token.markUsed();
        }

        return token;
    }
}
