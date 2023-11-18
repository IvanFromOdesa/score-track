package com.teamk.scoretrack.module.commons.cache.redis.service;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Optional;

public abstract class HashedRedisService<CACHE_CONTEXT, ENTITY extends IdAware<ID>, ID> extends RedisService<CACHE_CONTEXT, ENTITY, ID> {
    private final HashOperations<String, ID, ENTITY> dao;

    @Autowired
    protected HashedRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.dao = redisTemplate.opsForHash();
    }

    /**
     * Save or update
     * @param ctx
     */
    @Override
    public ENTITY cache(CACHE_CONTEXT ctx) {
        ENTITY entity = fromContext(ctx);
        dao.put(provideHashStore(), entity.getId(), entity);
        return entity;
    }

    /**
     * {@link org.springframework.data.redis.core.HashOperations} Returns null if it cannot find an entity in Redis.
     * Wrap it into Optional.
     * @param id
     * @return
     */
    @Override
    public Optional<ENTITY> get(ID id) {
        return Optional.ofNullable(dao.get(provideHashStore(), id));
    }

    public Map<ID, ENTITY> getAllHash() {
        return dao.entries(provideHashStore());
    }

    @Override
    public void evict(ID id) {
        dao.delete(provideHashStore(), id);
    }

    protected abstract String provideHashStore();
}
