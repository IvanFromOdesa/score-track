package com.teamk.scoretrack.module.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import com.teamk.scoretrack.utils.IOUtils;
import com.teamk.scoretrack.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewershipPlanIoTests {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = IOUtils.withVpModule();
    }

    @Test
    void shouldSerializeSuccessfully() {
        ViewershipPlan plan = UserUtils.mockViewershipPlanWithPlannedViewership();

        String json = IOUtils.serialize(plan, objectMapper);

        assertNotNull(json, "Serialized JSON should not be null");
        assertTrue(json.contains("id"), "Serialized JSON should contain 'id' field");
        assertTrue(json.contains(plan.getId().toString()), "Serialized JSON should contain the plan's id value");
    }

    @Test
    void shouldDeserializeSuccessfully() {
        ViewershipPlan plan = UserUtils.mockViewershipPlanWithPlannedViewership();
        String json = IOUtils.serialize(plan, objectMapper);

        ViewershipPlan deserializedPlan = IOUtils.deserialize(json, ViewershipPlan.class, objectMapper);

        assertNotNull(deserializedPlan, "Deserialized ViewershipPlan should not be null");
        assertEquals(plan.getId(), deserializedPlan.getId(), "Plan id should match after deserialization");
        assertEquals(plan.getPlannedViewership(), deserializedPlan.getPlannedViewership(), "Planned viewership's should match after deserialization");
    }

    @Test
    void shouldSerializeSuccessfullyWithCustomApiPlan() {
        ViewershipPlan plan = UserUtils.mockViewershipPlanWithCustomAvailableApis();

        String json = IOUtils.serialize(plan, objectMapper);

        assertNotNull(json, "Serialized JSON should not be null");
        assertTrue(json.contains("customAvailableApis"), "Serialized JSON should contain 'customAvailableApis' field");
        assertTrue(json.contains(plan.getCustomAvailableApis().get(0).getName()), "Serialized JSON should contain the custom API plan value");
    }

    @Test
    void shouldDeserializeSuccessfullyWithCustomApiPlan() {
        ViewershipPlan plan = UserUtils.mockViewershipPlanWithCustomAvailableApis();
        String json = IOUtils.serialize(plan, objectMapper);

        ViewershipPlan deserializedPlan = IOUtils.deserialize(json, ViewershipPlan.class, objectMapper);

        assertNotNull(deserializedPlan, "Deserialized ViewershipPlan with custom API plan should not be null");
        assertEquals(plan.getCustomAvailableApis(), deserializedPlan.getCustomAvailableApis(), "Custom API plan should match after deserialization");
    }
}
