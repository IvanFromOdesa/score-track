package com.teamk.scoretrack.module.security.recaptcha.event.publisher;

import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.publisher.EmailNotifyPublisher;
import com.teamk.scoretrack.module.security.recaptcha.event.RecaptchaFailureEvent;
import org.springframework.stereotype.Component;

@Component
public class RecaptchaFailurePublisher extends EmailNotifyPublisher<NotificationEmail, RecaptchaFailureEvent> {
    @Override
    protected void log(RecaptchaFailureEvent event) {
        LOGGER.warn(event.getCause().msg().formatted(
                event.getAuthentication().loginname(),
                event.getAttemptedIp(),
                event.getAttemptedDevice()
        ));
    }
}
