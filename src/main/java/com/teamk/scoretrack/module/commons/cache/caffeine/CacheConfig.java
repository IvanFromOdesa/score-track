package com.teamk.scoretrack.module.commons.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.teamk.scoretrack.module.commons.cache.service.ManualCacheManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration("caffeineCacheConfig")
public class CacheConfig {
    public static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";
    @Value("${caching.default.ttl}")
    private Long defaultTtl;

    @Bean(name = CAFFEINE_CACHE_MANAGER)
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(Duration.ofMillis(defaultTtl)));
        return cacheManager;
    }

    @Bean
    public ManualCacheManager manualCacheManager(@Qualifier(CAFFEINE_CACHE_MANAGER) CacheManager cacheManager) {
        return new ManualCacheManager(cacheManager);
    }
}
