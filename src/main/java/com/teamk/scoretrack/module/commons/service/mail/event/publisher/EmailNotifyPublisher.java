package com.teamk.scoretrack.module.commons.service.mail.event.publisher;

import com.teamk.scoretrack.module.commons.event.BaseEventPublisher;
import com.teamk.scoretrack.module.commons.service.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.service.mail.event.EmailNotifyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EmailNotifyPublisher<EMAIL_CONTEXT extends NotificationEmail, EVENT extends EmailNotifyEvent<EMAIL_CONTEXT>> extends BaseEventPublisher<EVENT> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(EmailNotifyPublisher.class);
    private static final int LOG_PRIO = EmailNotifyEvent.Cause.HIGHEST_PRIO - 1;

    public void processEvent(EVENT event) {
        super.processEvent(event);
        if (event.getCause().prio() >= LOG_PRIO) {
            log(event);
        }
    }

    protected void log(EVENT event) {

    }
}
