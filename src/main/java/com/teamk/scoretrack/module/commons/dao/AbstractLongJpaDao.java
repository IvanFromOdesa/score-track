package com.teamk.scoretrack.module.commons.dao;

import com.teamk.scoretrack.module.commons.domain.Identifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository for SQL DAOs. Uses {@link Long} type as ID.<br>
 * Not suitable in case of composite primary keys OR if you for some reason decide to use different type of ID.
 * @param <ENTITY>
 */
@NoRepositoryBean
public interface AbstractLongJpaDao<ENTITY extends Identifier> extends JpaRepository<ENTITY, Long> {
}
