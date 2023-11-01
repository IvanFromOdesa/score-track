package com.teamk.scoretrack.module.core.entities.user.fan.dao;

import com.teamk.scoretrack.module.core.entities.user.base.dao.AbstractUserDao;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import org.springframework.stereotype.Repository;

@Repository
public interface FanDao extends AbstractUserDao<Fan> {
}
