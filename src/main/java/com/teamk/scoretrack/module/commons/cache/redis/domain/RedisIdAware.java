package com.teamk.scoretrack.module.commons.cache.redis.domain;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;

public interface RedisIdAware<ID> extends IdAware<ID> {
    Long getExpiration();
    Long getTtl();
}
