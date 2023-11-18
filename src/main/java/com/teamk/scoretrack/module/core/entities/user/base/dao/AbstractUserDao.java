package com.teamk.scoretrack.module.core.entities.user.base.dao;

import com.teamk.scoretrack.module.commons.base.dao.AbstractLongJpaDao;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.Instant;
import java.util.List;

@NoRepositoryBean
public interface AbstractUserDao<ENTITY extends User> extends AbstractLongJpaDao<ENTITY> {
    List<ENTITY> findUsersByLastSeen(Instant lastSeen);
    List<ENTITY> findUsersByPreferredLang(Language language);
}
