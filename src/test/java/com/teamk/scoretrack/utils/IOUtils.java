package com.teamk.scoretrack.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import com.teamk.scoretrack.module.core.entities.user.client.io.ViewershipPlanDeserializer;
import com.teamk.scoretrack.module.core.entities.user.client.io.ViewershipPlanSerializer;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import com.teamk.scoretrack.module.core.entities.user.fan.io.FanDeserializer;
import com.teamk.scoretrack.module.core.entities.user.fan.io.FanSerializer;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.io.AuthenticationBeanDeserializer;
import com.teamk.scoretrack.module.security.auth.io.AuthenticationBeanSerializer;

public final class IOUtils {
    public static ObjectMapper getBaseObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        /*objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);*/
        objectMapper.registerModule(timeModule);
        return objectMapper;
    }

    public static ObjectMapper withVpModule() {
        ObjectMapper objectMapper = getBaseObjectMapper();
        SimpleModule vpModule = new SimpleModule();
        vpModule.addSerializer(ViewershipPlan.class, new ViewershipPlanSerializer());
        vpModule.addDeserializer(ViewershipPlan.class, new ViewershipPlanDeserializer());
        objectMapper.registerModule(vpModule);
        return objectMapper;
    }

    public static ObjectMapper withFanModule() {
        ObjectMapper objectMapper = withVpModule();
        SimpleModule fanModule = new SimpleModule();
        fanModule.addSerializer(Fan.class, new FanSerializer());
        fanModule.addDeserializer(Fan.class, new FanDeserializer());
        objectMapper.registerModule(fanModule);
        return objectMapper;
    }

    public static ObjectMapper withAuthBeanModule() {
        ObjectMapper mapper = withFanModule();
        SimpleModule authModule = new SimpleModule();
        authModule.addSerializer(AuthenticationBean.class, new AuthenticationBeanSerializer());
        authModule.addDeserializer(AuthenticationBean.class, new AuthenticationBeanDeserializer());
        mapper.registerModule(authModule);
        return mapper;
    }

    public static <T> String serialize(T obj, ObjectMapper mapper) {
        String json;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("::SERIALIZED JSON::");
        System.out.println(json);
        return json;
    }

    public static <T> T deserialize(String json, Class<T> tClass, ObjectMapper mapper) {
        try {
            T obj = mapper.readValue(json, tClass);
            System.out.println("::DESERIALIZED OBJECT::");
            System.out.println(mapper.writeValueAsString(obj));
            return obj;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
