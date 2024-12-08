package com.teamk.scoretrack.module.core.entities.user.support.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Role;
import com.teamk.scoretrack.module.core.entities.user.base.io.UserDeserializer;
import com.teamk.scoretrack.module.core.entities.user.support.domain.SupportUser;
import org.springframework.stereotype.Component;

@Component
public class SupportUserDeserializer extends UserDeserializer<SupportUser> {
    @Override
    public Class<SupportUser> getDeserializationClass() {
        return SupportUser.class;
    }

    @Override
    protected SupportUser deserializeUser(ObjectNode node, JsonParser p, DeserializationContext ctxt) {
        SupportUser supportUser = super.deserializeUser(node, p, ctxt);
        supportUser.setRole(Role.byRoleAlias(node.get("role").asText()));
        return supportUser;
    }

    @Override
    protected SupportUser createInstance() {
        return new SupportUser();
    }
}
