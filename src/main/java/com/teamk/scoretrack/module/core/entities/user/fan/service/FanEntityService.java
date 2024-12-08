package com.teamk.scoretrack.module.core.entities.user.fan.service;

import com.teamk.scoretrack.module.core.entities.user.client.service.AbstractClientUserEntityService;
import com.teamk.scoretrack.module.core.entities.user.fan.ctx.FanProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.fan.dao.FanDao;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FanEntityService extends AbstractClientUserEntityService<Fan, FanDao, FanProcessingContext> {
    @Override
    public void processUserCreation(FanProcessingContext ctx) {
        baseTransactionManager.doInNewTransaction(() -> save(processClientUser(ctx, new Fan())));
    }

    @Override
    public void processUserUpdate(FanProcessingContext ctx) {
        baseTransactionManager.doInNewTransaction(
                () -> update(processClientUser(ctx, new Fan()),
                () -> dao.findByAuthenticationBean(ctx.authenticationBean()))
        );
    }

    @Override
    protected Fan merge(Fan e, Fan byId) {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        mapper.typeMap(Fan.class, Fan.class).addMappings(m -> {
            m.skip(Fan::setProfile);
            m.skip(Fan::setViewershipPlan);
        });

        if (e.getProfile() != null) {
            mapper.map(e.getProfile(), byId.getProfile());
        }

        if (e.getViewershipPlan() != null) {
            mapper.map(e.getViewershipPlan(), byId.getViewershipPlan());
        }

        mapper.map(e, byId);

        byId.setAuthenticationBean(entityManager.merge(byId.getAuthenticationBean()));
        byId.setProfile(entityManager.merge(byId.getProfile()));
        byId.setViewershipPlan(entityManager.merge(byId.getViewershipPlan()));

        return byId;
    }

    @Override
    @Autowired
    protected void setDao(FanDao dao) {
        this.dao = dao;
    }
}
