package com.teamk.scoretrack.module.commons.cache.redis.domain;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;

import java.io.Serializable;

public abstract class RedisAuthIdentifier implements Serializable, IdAware<String> {
    private final String id;

    public RedisAuthIdentifier(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
