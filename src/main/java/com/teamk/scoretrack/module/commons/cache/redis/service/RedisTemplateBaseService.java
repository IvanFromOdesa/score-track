package com.teamk.scoretrack.module.commons.cache.redis.service;

import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisIdAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

// TODO: improve. More functionality needed
public abstract class RedisTemplateBaseService<CACHE_CONTEXT, ENTITY extends RedisIdAware<String>> extends RedisService<CACHE_CONTEXT, ENTITY, String> {
    protected RedisTemplate<String, Object> redisTemplate;

    @Override
    public ENTITY cache(CACHE_CONTEXT cacheContext) {
        ENTITY e = fromContext(cacheContext);
        redisTemplate.opsForValue().set(withNamespace(e.getId()), e, e.getTtl(), TimeUnit.SECONDS);
        return e;
    }

    /**
     * Use {@link #get(String, Class)} instead.
     * @throws UnsupportedOperationException not supported.
     */
    @Override
    public Optional<ENTITY> get(String key) {
        throw new UnsupportedOperationException("This method is not supported. Use #get(String, Class) instead.");
    }

    public Optional<ENTITY> get(String key, Class<ENTITY> entityClass) {
        return Optional.ofNullable(entityClass.cast(redisTemplate.opsForValue().get(withNamespace(key))));
    }

    @Override
    public void evict(String key) {
        redisTemplate.delete(withNamespace(key));
    }

    private String withNamespace(String key) {
        return String.format("%s:%s", getNamespace(), key);
    }

    protected abstract String getNamespace();

    /**
     * Default redis template is under the {@link com.teamk.scoretrack.module.commons.cache.redis.CacheConfig}.
     * Override if needed.
     */
    @Autowired
    protected void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
