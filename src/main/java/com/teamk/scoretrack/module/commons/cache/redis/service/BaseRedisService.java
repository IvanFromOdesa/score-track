package com.teamk.scoretrack.module.commons.cache.redis.service;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.commons.domain.IdAware;

import java.util.Optional;

/**
 * @param <CACHE_CONTEXT> from what we produce entity
 * @param <ENTITY> what is to be put into cache (produced based on CACHE_CONTEXT)
 * @param <ID> id of ENTITY
 * @param <DAO> redis type DAO
 */
public abstract class BaseRedisService<CACHE_CONTEXT, ENTITY extends IdAware<ID>, ID, DAO extends RedisDao<ENTITY, ID>> extends RedisService<CACHE_CONTEXT, ENTITY, ID> {
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

    protected abstract void setDao(DAO dao);
}
