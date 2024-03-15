package com.teamk.scoretrack.module.security.handler.error.authfailure.event.publisher;

import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.publisher.EmailNotifyPublisher;
import com.teamk.scoretrack.module.security.handler.error.authfailure.event.BadCredentialsAuthFailureEvent;
import org.springframework.stereotype.Component;

@Component
public class BadCredentialsAuthFailurePublisher extends EmailNotifyPublisher<NotificationEmail, BadCredentialsAuthFailureEvent> {
    @Override
    protected void log(BadCredentialsAuthFailureEvent event) {
        LOGGER.warn(event.getCause().msg().formatted(
                event.getAuthentication(),
                event.getIssuedAt(),
                event.getAttemptedDevice(),
                event.getAttemptedIp()
        ));
    }
}
