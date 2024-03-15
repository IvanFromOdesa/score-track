package com.teamk.scoretrack.module.core.entities.user.client.dao;

import com.teamk.scoretrack.module.core.entities.user.base.dao.AbstractUserDao;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientUserDao extends AbstractUserDao<ClientUser> {
    @Override
    @javax.annotation.Nonnull
    @EntityGraph(value = ClientUser.CLIENT_EAGER)
    Optional<ClientUser> findById(@javax.annotation.Nonnull Long id);
}
