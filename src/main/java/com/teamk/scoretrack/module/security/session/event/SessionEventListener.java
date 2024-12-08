package com.teamk.scoretrack.module.security.session.event;

import com.teamk.scoretrack.module.security.session.service.SessionExpirationAlertRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionEventListener {
    private final SessionExpirationAlertRedisService expirationAlertRedisService;

    @Autowired
    public SessionEventListener(SessionExpirationAlertRedisService expirationAlertRedisService) {
        this.expirationAlertRedisService = expirationAlertRedisService;
    }

    @EventListener
    public void processSessionDeletedEvent(SessionDeletedEvent event) {
        // This is not necessary, as the session-expiration-alert expires before session data
        expirationAlertRedisService.evict(event.getSessionId());
    }

    @EventListener
    public void processSessionDestroyedEvent(SessionDestroyedEvent event) {
        expirationAlertRedisService.evict(event.getSessionId());
    }

    @EventListener
    public void processSessionExpiredEvent(SessionExpiredEvent event) {
        expirationAlertRedisService.evict(event.getSessionId());
    }
}
