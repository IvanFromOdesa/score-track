package com.teamk.scoretrack.module.commons.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public abstract class BaseEventPublisher<EVENT> {
    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;

    public void processEvent(EVENT event) {
        applicationEventPublisher.publishEvent(event);
    }
}
