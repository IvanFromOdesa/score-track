package com.teamk.scoretrack.module.security.auth.dao;

import com.teamk.scoretrack.module.commons.dao.AbstractLongJpaDao;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationDao extends AbstractLongJpaDao<AuthenticationBean> {
    Optional<AuthenticationBean> findByLoginname(String loginname);
    Optional<AuthenticationBean> findByEmail(String email);
}
