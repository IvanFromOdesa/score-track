package com.teamk.scoretrack.module.core.entities.user.fan.event;

import com.teamk.scoretrack.module.core.entities.user.base.event.listener.AbstractUserProcessingListener;
import com.teamk.scoretrack.module.core.entities.user.fan.ctx.FanProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.fan.service.FanEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanProcessingListener extends AbstractUserProcessingListener<FanProcessingEvent, FanProcessingContext, FanEntityService> {
    @Override
    @Autowired
    protected void setUserEntityService(FanEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }
}
