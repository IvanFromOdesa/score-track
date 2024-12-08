package com.teamk.scoretrack.module.core.entities.user.client.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.core.entities.user.base.io.UserDeserializer;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;

public abstract class ClientUserDeserializer<C extends ClientUser> extends UserDeserializer<C> {
    @Override
    protected C deserializeUser(ObjectNode node, JsonParser p, DeserializationContext ctxt) {
        C user = super.deserializeUser(node, p, ctxt);
        JsonNode viewershipPlanNode = node.get("viewershipPlan");

        ViewershipPlan viewershipPlan = CommonsUtil.orNull(viewershipPlanNode, vpNode -> {
            try {
                return p.getCodec().treeToValue(vpNode, ViewershipPlan.class);
            } catch (JsonProcessingException e) {
                throw new ServerException(e);
            }
        });

        user.setViewershipPlan(viewershipPlan);
        return user;
    }
}
