package com.teamk.scoretrack.module.security.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AuthenticationConfiguration {
    public static final String BANNED_WORDS = "authSignUpBannedWordsList";

    // Avoid reading / loading into memory every invocation
    @Bean(BANNED_WORDS)
    public List<String> bannedWords(ObjectMapper mapper) {
        return CommonsUtil.readResourcesIntoCollection(mapper, "valid/banned.json", String.class);
    }
}
