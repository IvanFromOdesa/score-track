package com.teamk.scoretrack.module.core.api.commons.rate_limiter;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiDailyRequestDao extends RedisDao<ApiDailyRequest, String> {
}
