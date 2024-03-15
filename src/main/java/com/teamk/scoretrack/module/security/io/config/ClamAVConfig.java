package com.teamk.scoretrack.module.security.io.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.capybara.clamav.ClamavClient;

@Configuration
public class ClamAVConfig {
    @Value("${clamAV.hostname}")
    private String hostname;
    @Value("${clamAV.port}")
    private int port;

    @Bean
    public ClamavClient clamavClient() {
        ClamavClient clamavClient = new ClamavClient(hostname, port);
        /*if (!clamavClient.isReachable(4000)) {
            throw new IllegalStateException("ClamAV instance is not reachable.");
        }*/
        return clamavClient;
    }
}
