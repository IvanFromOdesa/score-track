package com.teamk.scoretrack.module.commons.mail.resend.dao;

import com.teamk.scoretrack.module.commons.cache.redis.dao.RedisDao;
import com.teamk.scoretrack.module.commons.mail.resend.domain.ResendNotificationEmail;
import org.springframework.stereotype.Repository;

@Repository
public interface ResendNotificationEmailDao extends RedisDao<ResendNotificationEmail, String> {
}
