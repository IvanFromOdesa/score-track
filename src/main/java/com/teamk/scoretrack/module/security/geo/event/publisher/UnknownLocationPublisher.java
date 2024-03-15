package com.teamk.scoretrack.module.security.geo.event.publisher;

import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.publisher.EmailNotifyPublisher;
import com.teamk.scoretrack.module.security.geo.event.UnknownLocationEvent;
import org.springframework.stereotype.Component;

@Component
public class UnknownLocationPublisher extends EmailNotifyPublisher<NotificationEmail, UnknownLocationEvent> {
    @Override
    protected void log(UnknownLocationEvent event) {
        LOGGER.warn(event.getCause().msg().formatted(
                event.getAuthentication().getLoginname(),
                event.getAttemptedGeo().location(),
                event.getIssuedAt(),
                event.getAttemptedDevice(),
                event.getAttemptedIp())
        );
    }
}
