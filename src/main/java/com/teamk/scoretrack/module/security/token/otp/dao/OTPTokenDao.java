package com.teamk.scoretrack.module.security.token.otp.dao;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.security.token.otp.domain.OTPToken;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPTokenDao extends RedisDao<OTPToken, String> {
}
