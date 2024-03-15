package com.teamk.scoretrack;

import com.teamk.scoretrack.module.core.cloud.GoogleCloudKeysProps;
import com.teamk.scoretrack.module.security.commons.config.RsaKeyProperties;
import com.teamk.scoretrack.module.security.recaptcha.RecaptchaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties({RsaKeyProperties.class, RecaptchaKeyProperties.class, GoogleCloudKeysProps.class})
@EnableJpaAuditing
public class ScoreTrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreTrackApplication.class, args);
    }
}
