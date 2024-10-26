package com.teamk.scoretrack.module.security.pwdreset.dao;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.security.pwdreset.domain.PwdResetToken;
import org.springframework.stereotype.Repository;

@Repository
public interface PwdResetTokenDao extends RedisDao<PwdResetToken, String> {
}
