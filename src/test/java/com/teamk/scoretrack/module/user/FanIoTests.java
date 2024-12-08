package com.teamk.scoretrack.module.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import com.teamk.scoretrack.utils.IOUtils;
import com.teamk.scoretrack.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FanIoTests {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = IOUtils.withFanModule();
    }

    @Test
    void shouldSerializeSuccessfully() {
        Fan fan = UserUtils.mockFan();

        String json = IOUtils.serialize(fan, objectMapper);

        assertNotNull(json, "Serialized JSON should not be null");
        assertTrue(json.contains("id"), "Serialized JSON should contain 'id' field");
        assertTrue(json.contains(fan.getId().toString()), "Serialized JSON should contain the fan's id value");
    }

    @Test
    void shouldDeserializeSuccessfully() {
        Fan fan = UserUtils.mockFan();
        String json = IOUtils.serialize(fan, objectMapper);

        Fan deserializedFan = IOUtils.deserialize(json, Fan.class, objectMapper);

        assertNotNull(deserializedFan, "Deserialized Fan should not be null");
        assertEquals(fan.getId().toString(), deserializedFan.getId().toString(), "Fan id should match after deserialization");
    }
}
