package com.teamk.scoretrack;

import com.teamk.scoretrack.module.core.cloud.GoogleCloudKeysProps;
import com.teamk.scoretrack.module.security.commons.config.RsaKeyProperties;
import com.teamk.scoretrack.module.security.recaptcha.RecaptchaKeyProperties;
import com.teamk.scoretrack.script.ScriptInitializer;
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
        SpringApplication application = new SpringApplication(ScoreTrackApplication.class);
        application.addInitializers(new ScriptInitializer());
        application.run(args);
    }
}
