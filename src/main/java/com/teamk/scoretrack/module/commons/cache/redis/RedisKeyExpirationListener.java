package com.teamk.scoretrack.module.commons.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisKeyExpirationListener implements MessageListener {
    private final List<RedisKeyExpirationCallback> keyExpirationCallbacks;

    @Autowired
    public RedisKeyExpirationListener(List<RedisKeyExpirationCallback> keyExpirationCallbacks) {
        this.keyExpirationCallbacks = keyExpirationCallbacks;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody());
        keyExpirationCallbacks.forEach(c -> {
            if (expiredKey.startsWith(c.getNamespace())) {
                c.run(expiredKey);
            }
        });
    }
}
