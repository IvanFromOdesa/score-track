package com.teamk.scoretrack.module.commons.cache.redis.service;

import com.teamk.scoretrack.module.commons.domain.IdAware;

import java.util.Optional;

public abstract class RedisService<CACHE_CONTEXT, ENTITY extends IdAware<ID>, ID> {
    protected abstract ENTITY fromContext(CACHE_CONTEXT ctx);
    public abstract ENTITY cache(CACHE_CONTEXT context);
    public abstract Optional<ENTITY> get(ID id);
    public abstract void evict(ID id);
}
