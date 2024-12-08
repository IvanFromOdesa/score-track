package com.teamk.scoretrack.module.commons.cache.redis.service;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.commons.cache.redis.domain.RedisIdAware;

/**
 * Use if cache context = entity
 * @param <CTX>
 * @param <ID>
 * @param <DAO>
 */
public abstract class RedisEqualCtxService<CTX extends RedisIdAware<ID>, ID, DAO extends RedisDao<CTX, ID>> extends BaseRedisService<CTX, CTX, ID, DAO> {
    @Override
    protected CTX fromContext(CTX ctx) {
        return ctx;
    }
}
