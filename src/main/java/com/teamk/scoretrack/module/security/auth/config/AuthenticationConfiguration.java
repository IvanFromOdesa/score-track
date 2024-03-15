package com.teamk.scoretrack.module.security.auth.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AuthenticationConfiguration {
    public static final String BANNED_WORDS = "bannedWords";

    // Avoid reading / loading into memory every invocation
    @Bean(BANNED_WORDS)
    public List<String> bannedWords() {
        String path = "valid/banned.json";
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource != null) {
            File file = new File(resource.getFile());
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(file, new TypeReference<>() {});
            } catch (IOException e) {
                MessageLogger.error(e.getMessage(), e);
            }
        } else {
            MessageLogger.error("Can't get the resource file from FS: %s".formatted(path));
        }
        return new ArrayList<>();
    }
}
