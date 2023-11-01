package com.teamk.scoretrack.module.security.geo.config;

import com.maxmind.geoip2.DatabaseReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class GeoLiteConfiguration {
    @Bean
    @ConditionalOnProperty(value = "geo.enabled", havingValue = "true")
    public DatabaseReader databaseReader() throws IOException {
        File resource = new File(getClass().getClassLoader().getResource("geo/GeoLite2-City.mmdb").getFile());
        return new DatabaseReader.Builder(resource).build();
    }
}
