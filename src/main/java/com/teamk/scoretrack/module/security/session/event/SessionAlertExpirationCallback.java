package com.teamk.scoretrack.module.security.session.event;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.redis.RedisKeyExpirationCallback;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class SessionAlertExpirationCallback implements RedisKeyExpirationCallback {
    public static final String CHANNEL_NAMESPACE = "session-expiration-alert";

    private final JedisPool jedisPool;

    @Autowired
    public SessionAlertExpirationCallback(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void run(String expiredKey) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(CHANNEL_NAMESPACE, expiredKey);
        } catch (Exception ex) {
            MessageLogger.error("Error while publishing message to Redis: %s".formatted(ex.getMessage()));
        }
    }

    @Override
    public String getNamespace() {
        return CacheStore.SESSION_EXPIRATION_ALERT;
    }
}
