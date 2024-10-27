package com.teamk.scoretrack.module.core.entities.user.base.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.core.entities.user.base.ctx.UserProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.base.dao.AbstractUserDao;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;

public abstract class AbstractUserEntityService<USER extends User, DAO extends AbstractUserDao<USER>, USER_CTX extends UserProcessingContext> extends AbstractJpaEntityService<USER, Long, DAO> {
    public abstract void processUserCreation(USER_CTX ctx);
    public abstract void processUserUpdate(USER_CTX ctx);
}
