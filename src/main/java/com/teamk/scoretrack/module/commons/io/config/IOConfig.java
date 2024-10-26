package com.teamk.scoretrack.module.commons.io.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teamk.scoretrack.module.commons.io.CustomDeserializer;
import com.teamk.scoretrack.module.commons.io.StandardLocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class IOConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper(List<CustomDeserializer<?>> deserializers) {
        ObjectMapper mapper = getDefault();
        SimpleModule module = new SimpleModule();
        for (CustomDeserializer<?> deserializer: deserializers) {
            addDeserializerToModule(module, deserializer);
        }
        mapper.registerModule(module);
        return mapper;
    }

    private <T> void addDeserializerToModule(SimpleModule module, CustomDeserializer<T> deserializer) {
        module.addDeserializer(deserializer.getDeserializationClass(), deserializer.getDeserializer());
    }

    public static ObjectMapper getDefault() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDate.class, new StandardLocalDateSerializer());
        objectMapper.registerModule(module);
        objectMapper.registerModule(new GuavaModule());
        return objectMapper;
    }
}
