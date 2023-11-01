package com.teamk.scoretrack.module.core.entities.user.base.event.publisher;

import com.teamk.scoretrack.module.core.entities.user.base.ctx.UserProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.base.event.UserProcessingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserProcessingDelegator {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserProcessingDelegator(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public <EVENT extends UserProcessingEvent<USER_CTX>, USER_CTX extends UserProcessingContext> void processEvent(EVENT event) {
        applicationEventPublisher.publishEvent(event);
    }
}
