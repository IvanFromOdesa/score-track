package com.teamk.scoretrack.module.core.entities.user.base.event.listener;

import com.teamk.scoretrack.module.commons.event.BaseEventListener;
import com.teamk.scoretrack.module.core.entities.user.base.ctx.UserProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.base.event.UserProcessingEvent;
import com.teamk.scoretrack.module.core.entities.user.base.service.AbstractUserEntityService;
import org.springframework.context.event.EventListener;

public abstract class AbstractUserProcessingListener<EVENT extends UserProcessingEvent<USER_CTX>, USER_CTX extends UserProcessingContext, USER_SERVICE extends AbstractUserEntityService<?, ?, USER_CTX>> extends BaseEventListener<EVENT> {
    protected USER_SERVICE userEntityService;

    @EventListener
    public void handleEvent(EVENT event) {
        // TODO
        if (event.getType().isCreate()) {
            userEntityService.processUserCreation(event.getCtx());
        } else if (event.getType().isUpdate()) {
            userEntityService.processUserUpdate(event.getCtx());
        }
    }

    protected abstract void setUserEntityService(USER_SERVICE userEntityService);
}
