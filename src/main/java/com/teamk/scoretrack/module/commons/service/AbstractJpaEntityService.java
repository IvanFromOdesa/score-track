package com.teamk.scoretrack.module.commons.service;

import com.teamk.scoretrack.module.commons.domain.IdAware;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractJpaEntityService<ENTITY extends IdAware<ID>, ID, DAO extends JpaRepository<ENTITY, ID>> extends AbstractEntityService<ENTITY, ID, DAO> {

}
