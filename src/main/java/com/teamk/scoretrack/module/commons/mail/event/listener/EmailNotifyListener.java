package com.teamk.scoretrack.module.commons.mail.event.listener;

import com.teamk.scoretrack.module.commons.event.BaseEventListener;
import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.EmailNotifyEvent;
import com.teamk.scoretrack.module.commons.mail.resend.domain.ResendNotificationEmail;
import com.teamk.scoretrack.module.commons.mail.resend.service.ResendNotificationEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class EmailNotifyListener<EMAIL_CONTEXT extends NotificationEmail, EVENT extends EmailNotifyEvent<EMAIL_CONTEXT>> extends BaseEventListener<EVENT> {
    protected IEmailService<EMAIL_CONTEXT> emailService;
    @Autowired
    protected ResendNotificationEmailService<ResendNotificationEmail> resendNotificationEmailService;
    @Value("${spring.application.name}")
    protected String issuer;
    @Value("${mail.support.address}")
    protected String supportEmail;

    protected void cache(String id, NotificationEmail email) {
        ResendNotificationEmail ctx = new ResendNotificationEmail(email, id);
        ctx.setAttempt(ctx.getAttempt() + 1);
        resendNotificationEmailService.cache(ctx);
    }

    protected abstract void setEmailService(IEmailService<EMAIL_CONTEXT> emailContext);
}
