package com.teamk.scoretrack.module.security.history.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.security.history.dao.AbstractHistoryDao;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;

public abstract class AbstractHistoryService<ENTITY extends AuthenticationHistory, DAO extends AbstractHistoryDao<ENTITY>> extends AbstractJpaEntityService<ENTITY, Long, DAO> {

}
