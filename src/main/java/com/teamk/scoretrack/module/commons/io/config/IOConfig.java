package com.teamk.scoretrack.module.commons.io.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teamk.scoretrack.module.commons.io.CustomDeserializer;
import com.teamk.scoretrack.module.commons.io.ISerializationClassAware;
import com.teamk.scoretrack.module.commons.io.StandardLocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class IOConfig {
    @Bean
    @Primary
    @Scope("prototype")
    public <S extends JsonSerializer<S> & ISerializationClassAware<S>> ObjectMapper objectMapper(List<S> serializers, List<CustomDeserializer<?>> deserializers) {
        ObjectMapper mapper = getDefault();
        SimpleModule module = new SimpleModule();
        addSerializers(serializers, module);
        addDeserializers(deserializers, module);
        mapper.registerModule(module);
        return mapper;
    }

    public static void addDeserializers(List<CustomDeserializer<?>> deserializers, SimpleModule module) {
        for (CustomDeserializer<?> deserializer: deserializers) {
            addDeserializerToModule(module, deserializer);
        }
    }

    public static <T extends JsonSerializer<T> & ISerializationClassAware<T>> void addSerializers(List<T> serializers, SimpleModule module) {
        for (T serializer: serializers) {
            module.addSerializer(serializer.getSerializationClass(), serializer);
        }
    }

    private static <T> void addDeserializerToModule(SimpleModule module, CustomDeserializer<T> deserializer) {
        module.addDeserializer(deserializer.getDeserializationClass(), deserializer);
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
