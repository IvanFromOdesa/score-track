package com.teamk.scoretrack.module.core.api.nbaapi.commons.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class APINba {
    @Value("${nbaapi.realhost}")
    private String realHost;
    @Value("${nbaapi.key.name}")
    private String keyName;
    @Value("${nbaapi.key.value}")
    private String keyValue;
    @Value("${nbaapi.host.name}")
    private String hostName;
    @Value("${nbaapi.host.value}")
    private String hostValue;
    @Value("${nbaapi.connection.timeout}")
    private long connectTimeout;
    @Value("${nbaapi.read.timeout}")
    private long readTimeout;
    public static final String NAME = "nbaApi";

    @Bean(NAME)
    public RestTemplate nbaApi(RestTemplateBuilder builder) {
        return builder
                .customizers(rt -> rt.getInterceptors().add((request, body, execution) -> {
                    HttpHeaders headers = request.getHeaders();
                    headers.add(keyName, keyValue);
                    headers.add(hostName, hostValue);
                    return execution.execute(request, body);
                }))
                .rootUri(realHost)
                .setConnectTimeout(Duration.ofSeconds(connectTimeout))
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .build();
    }
}
