package com.teamk.scoretrack.module.core.api.commons.rate_limiter;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RedisHash(value = CacheStore.API_RATE_LIMITER, timeToLive = ApiDailyRequest.TTL)
public class ApiDailyRequest implements Serializable, IdAware<String> {
    @Id
    private final String apiName;
    /**
     * Keeps track of number requests per collection
     */
    private final Map<String, Integer> collectionReqCount;
    private int totalReqCount;

    public static final long TTL = 86400; // 24 h
    @Serial
    private static final long serialVersionUID = 2373243245261805470L;

    public ApiDailyRequest(String apiName) {
        this.apiName = apiName;
        this.totalReqCount = 1;
        this.collectionReqCount = new HashMap<>();
    }

    public String getApiName() {
        return apiName;
    }

    public Map<String, Integer> getCollectionReqCount() {
        return collectionReqCount;
    }

    public int getTotalReqCount() {
        return totalReqCount;
    }

    public void setTotalReqCount(int totalReqCount) {
        this.totalReqCount = totalReqCount;
    }

    @Override
    public String getId() {
        return apiName;
    }
}
