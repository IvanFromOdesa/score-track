package com.teamk.scoretrack.module.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.utils.AuthenticationBeanUtils;
import com.teamk.scoretrack.utils.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationBeanIoTests {
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = IOUtils.withAuthBeanModule();
    }

    @Test
    void shouldSerializeSuccessfully() {
        AuthenticationBean authenticationBean = AuthenticationBeanUtils.mockAuthenticationBean();

        String json = IOUtils.serialize(authenticationBean, mapper);

        assertNotNull(json, "Serialized JSON should not be null");
        assertTrue(json.contains("loginname"), "Serialized JSON should contain 'loginname' field");
        assertTrue(json.contains(authenticationBean.getLoginname()), "Serialized JSON should contain the loginname value");
    }

    // FIXME: this one test
    @Test
    void shouldDeserializeSuccessfully() {
        AuthenticationBean authenticationBean = AuthenticationBeanUtils.mockAuthenticationBean();
        String json = IOUtils.serialize(authenticationBean, mapper);

        AuthenticationBean deserializedBean = IOUtils.deserialize(json, AuthenticationBean.class, mapper);

        assertNotNull(deserializedBean, "Deserialized AuthenticationBean should not be null");
        assertEquals(authenticationBean.getLoginname(), deserializedBean.getLoginname(), "Loginname should match after deserialization");
        assertEquals(authenticationBean.getEmail(), deserializedBean.getEmail(), "Email should match after deserialization");
        assertEquals(authenticationBean.getStatus(), deserializedBean.getStatus(), "Status should match after deserialization");
    }
}
