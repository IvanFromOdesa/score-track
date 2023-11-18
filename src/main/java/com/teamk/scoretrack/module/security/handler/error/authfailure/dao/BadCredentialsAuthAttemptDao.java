package com.teamk.scoretrack.module.security.handler.error.authfailure.dao;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.BadCredentialsAuthenticationAttempt;
import org.springframework.stereotype.Repository;

@Repository
public interface BadCredentialsAuthAttemptDao extends RedisDao<BadCredentialsAuthenticationAttempt, String> {
}
