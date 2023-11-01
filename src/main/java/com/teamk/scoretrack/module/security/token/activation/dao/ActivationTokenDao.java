package com.teamk.scoretrack.module.security.token.activation.dao;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.security.token.activation.domain.ActivationToken;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivationTokenDao extends RedisDao<ActivationToken, UUID> {
}
