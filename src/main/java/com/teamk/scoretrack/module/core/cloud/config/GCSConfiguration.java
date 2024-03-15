package com.teamk.scoretrack.module.core.cloud.config;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.teamk.scoretrack.module.core.cloud.GoogleCloudKeysProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Objects;

@Configuration
public class GCSConfiguration {
    @Bean
    public Storage googleCloudStorage(GoogleCloudKeysProps props) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(props.auth())));
        return StorageOptions.newBuilder().setCredentials(credentials).setProjectId(props.projectId()).build().getService();
    }
}
