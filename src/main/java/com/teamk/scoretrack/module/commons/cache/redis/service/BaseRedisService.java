package com.teamk.scoretrack.module.commons.cache.redis.service;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisIdAware;

import java.util.Optional;

/**
 * @param <CACHE_CONTEXT> from what we produce entity
 * @param <ENTITY> what is to be put into cache (produced based on CACHE_CONTEXT)
 * @param <ID> id of ENTITY
 * @param <DAO> redis type DAO
 */
public abstract class BaseRedisService<CACHE_CONTEXT, ENTITY extends RedisIdAware<ID>, ID, DAO extends RedisDao<ENTITY, ID>> extends RedisService<CACHE_CONTEXT, ENTITY, ID> {
    protected DAO dao;

    @Override
    public ENTITY cache(CACHE_CONTEXT ctx) {
        return dao.save(fromContext(ctx));
    }

    @Override
    public Optional<ENTITY> get(ID id) {
        return dao.findById(id);
    }

    @Override
    public void evict(ID id) {
        dao.deleteById(id);
    }

    public Optional<ENTITY> evictAndGet(ID id) {
        Optional<ENTITY> entity = get(id);
        if (entity.isPresent()) {
            evict(id);
        }
        return entity;
    }

    protected abstract void setDao(DAO dao);
}
