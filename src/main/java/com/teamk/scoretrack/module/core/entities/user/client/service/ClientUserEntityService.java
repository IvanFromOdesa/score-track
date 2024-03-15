package com.teamk.scoretrack.module.core.entities.user.client.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.core.entities.user.client.dao.ClientUserDao;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientUserEntityService extends AbstractJpaEntityService<ClientUser, Long, ClientUserDao> {
    @Override
    @Autowired
    protected void setDao(ClientUserDao dao) {
        this.dao = dao;
    }
}
