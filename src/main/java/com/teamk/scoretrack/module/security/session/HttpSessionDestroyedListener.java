package com.teamk.scoretrack.module.security.session;

import com.teamk.scoretrack.module.commons.event.BaseEventListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class HttpSessionDestroyedListener extends BaseEventListener<HttpSessionDestroyedEvent> {
    @Override
    @EventListener
    public void handleEvent(HttpSessionDestroyedEvent httpSessionCreatedEvent) {
        // TODO: metrics about session calling external service
    }
}
