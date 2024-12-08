package com.teamk.scoretrack.module.core.entities.user.base.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.teamk.scoretrack.module.commons.io.CustomDeserializer;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserPrivilegeDeserializer extends CustomDeserializer<UserPrivilege> {
    @Override
    public Class<UserPrivilege> getDeserializationClass() {
        return UserPrivilege.class;
    }

    @Override
    public UserPrivilege deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String authority = node.get("authority").asText();
        JsonNode subAuthoritiesNode = node.get("subAuthorities");

        int[] subAuthorities = null;
        if (subAuthoritiesNode != null && subAuthoritiesNode.isArray()) {
            subAuthorities = new int[subAuthoritiesNode.size()];
            for (int i = 0; i < subAuthoritiesNode.size(); i++) {
                subAuthorities[i] = subAuthoritiesNode.get(i).asInt();
            }
        }

        return new UserPrivilege(authority, subAuthorities);
    }
}
