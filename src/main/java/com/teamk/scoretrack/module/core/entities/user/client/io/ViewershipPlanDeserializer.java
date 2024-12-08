package com.teamk.scoretrack.module.core.entities.user.client.io;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.teamk.scoretrack.module.commons.io.CustomDeserializer;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.core.entities.sport_api.SportAPI;
import com.teamk.scoretrack.module.core.entities.user.client.domain.PlannedViewership;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ViewershipPlanDeserializer extends CustomDeserializer<ViewershipPlan> {

    @Override
    public ViewershipPlan deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectNode node = p.getCodec().readTree(p);

        // TO CHECK: currently returns 0 if id is not present in the JSON.
        // Fix if needed: n -> n != null && n.hasNonNull("id") ? n.asLong() : null
        Long id = CommonsUtil.orNull(node.get("id"), JsonNode::asLong);

        Instant createdAt = CommonsUtil.orNull(node.get("createdAt"), n -> Instant.ofEpochMilli(n.asLong()));
        Instant endDateTime = CommonsUtil.orNull(node.get("endDateTime"), n -> Instant.ofEpochMilli(n.asLong()));

        List<SportAPI> customAvailableApis = deserializeSportApis(node.get("customAvailableApis"));
        PlannedViewership plannedViewership = CommonsUtil.orNull(node.get("plannedViewership"), this::deserializePlannedViewership);

        ViewershipPlan plan = new ViewershipPlan();
        plan.setId(id);
        plan.setCreatedAt(createdAt);
        plan.setEndDateTime(endDateTime);
        plan.setCustomAvailableApis(customAvailableApis);
        plan.setPlannedViewership(plannedViewership);

        return plan;
    }

    private PlannedViewership deserializePlannedViewership(JsonNode node) {
        return EnumUtils.deserializeEnumValue(node, PlannedViewership.UNDEFINED);
    }

    private List<SportAPI> deserializeSportApis(JsonNode node) {
        if (node != null && node.isArray()) {
            return StreamSupport.stream(node.spliterator(), false)
                    .map(n -> EnumUtils.deserializeEnumValue(n, SportAPI.UNDEFINED))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public Class<ViewershipPlan> getDeserializationClass() {
        return ViewershipPlan.class;
    }
}
