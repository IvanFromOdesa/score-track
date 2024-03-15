package com.teamk.scoretrack.module.security.ipblocker.dao;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.security.ipblocker.domain.IpAuthenticationAttempt;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAuthenticationAttemptDao extends RedisDao<IpAuthenticationAttempt, String> {
}
