package com.teamk.scoretrack.module.security.history.dao;

import com.teamk.scoretrack.module.commons.base.dao.AbstractLongJpaDao;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractHistoryDao<ENTITY extends AuthenticationHistory> extends AbstractLongJpaDao<ENTITY> {
}
