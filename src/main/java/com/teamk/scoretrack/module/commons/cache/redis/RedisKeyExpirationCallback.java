package com.teamk.scoretrack.module.commons.cache.redis;

public interface RedisKeyExpirationCallback {
    void run(String expiredKey);
    String getNamespace();
}
