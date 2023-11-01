package com.teamk.scoretrack.module.core.entities.user.fan.service;

import com.teamk.scoretrack.module.core.entities.user.fan.dao.FanDao;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import com.teamk.scoretrack.module.core.entities.user.fan.ctx.FanProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.base.service.AbstractUserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FanEntityService extends AbstractUserEntityService<Fan, FanDao, FanProcessingContext> {
    @Override
    public void processUserCreation(FanProcessingContext ctx) {
        baseTransactionManager.doInNewTransaction(() -> save(processBusinessUser(ctx, new Fan())));
    }

    @Override
    @Autowired
    protected void setDao(FanDao dao) {
        this.dao = dao;
    }
}
