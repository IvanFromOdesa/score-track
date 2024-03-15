package com.teamk.scoretrack.module.core.cloud;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.cloud")
public record GoogleCloudKeysProps(String auth, String projectId) {
}
