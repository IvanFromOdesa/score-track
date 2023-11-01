package com.teamk.scoretrack.module.commons.cache.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ManualCacheManager {
    private final CacheManager cacheManager;

    public ManualCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <KEY, VALUE> void cache(String cacheName, KEY key, VALUE value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    public <KEY, VALUE> VALUE evict(String cacheName, KEY key, Class<VALUE> clazz, Callable<VALUE> cacheMiss) throws Exception {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            VALUE value = cache.get(key, clazz);
            cache.evict(key);
            return value;
        }
        return cacheMiss.call();
    }

    public List<String> cacheNamesInUse() {
        return new ArrayList<>(cacheManager.getCacheNames());
    }
}
