package com.teamk.scoretrack.module.commons.mail.event.listener;

import com.teamk.scoretrack.module.commons.event.BaseEventListener;
import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.EmailNotifyEvent;

public abstract class EmailNotifyListener<EMAIL_CONTEXT extends NotificationEmail, EVENT extends EmailNotifyEvent<EMAIL_CONTEXT>> extends BaseEventListener<EVENT> {
    protected IEmailService<EMAIL_CONTEXT> emailService;
    protected abstract void setEmailService(IEmailService<EMAIL_CONTEXT> emailContext);
}
