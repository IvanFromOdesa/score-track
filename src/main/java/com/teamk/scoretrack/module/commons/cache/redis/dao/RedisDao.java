package com.teamk.scoretrack.module.commons.cache.redis.dao;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RedisDao<ENTITY, ID> extends KeyValueRepository<ENTITY, ID> {
}
