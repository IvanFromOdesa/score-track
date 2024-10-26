package com.teamk.scoretrack.module.core.entities.user.fan.service;

import com.teamk.scoretrack.module.core.entities.user.client.service.AbstractClientUserEntityService;
import com.teamk.scoretrack.module.core.entities.user.fan.ctx.FanProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.fan.dao.FanDao;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FanEntityService extends AbstractClientUserEntityService<Fan, FanDao, FanProcessingContext> {
    @Override
    public void processUserCreation(FanProcessingContext ctx) {
        baseTransactionManager.doInNewTransaction(() -> save(processClientUser(ctx, new Fan())));
    }

    @Override
    @Autowired
    protected void setDao(FanDao dao) {
        this.dao = dao;
    }
}
