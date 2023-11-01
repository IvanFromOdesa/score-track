package com.teamk.scoretrack.module.security.geo.event.publisher;

import com.teamk.scoretrack.module.commons.service.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.service.mail.event.publisher.EmailNotifyPublisher;
import com.teamk.scoretrack.module.security.geo.event.UnknownLocationEvent;
import org.springframework.stereotype.Component;

@Component
public class UnknownLocationPublisher extends EmailNotifyPublisher<NotificationEmail, UnknownLocationEvent> {
    @Override
    protected void log(UnknownLocationEvent event) {
        LOGGER.warn(event.getCause().msg().formatted(
                event.getAuthenticationBean().getLoginname(),
                event.getAttemptedCountry(),
                event.getIssuedAt(),
                event.getAttemptedDevice(),
                event.getAttemptedIp())
        );
    }
}
