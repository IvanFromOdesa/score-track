package com.teamk.scoretrack.module.core.api.commons.rate_limiter;

import com.teamk.scoretrack.module.commons.cache.redis.service.RedisEqualCtxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiDailyRequestService extends RedisEqualCtxService<ApiDailyRequest, String, ApiDailyRequestDao> {
    public void evictAll() {
        dao.deleteAll();
    }

    @Override
    @Autowired
    protected void setDao(ApiDailyRequestDao dao) {
        this.dao = dao;
    }
}
