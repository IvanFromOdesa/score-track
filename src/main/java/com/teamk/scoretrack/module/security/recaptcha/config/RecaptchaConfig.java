package com.teamk.scoretrack.module.security.recaptcha.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RecaptchaConfig {
    public static final String NAME = "googleRecaptcha";
    @Value("${recaptcha.connection-timeout}")
    private long connectionTimeout;
    @Value("${recaptcha.read-timeout}")
    private long readTimeout;

    @Bean(NAME)
    public RestTemplate recaptchaExternalService(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(connectionTimeout))
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .build();
    }
}
