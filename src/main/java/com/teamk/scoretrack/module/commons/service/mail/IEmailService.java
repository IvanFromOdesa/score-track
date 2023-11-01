package com.teamk.scoretrack.module.commons.service.mail;

public interface IEmailService<EMAIL_CONTEXT extends NotificationEmail> {
    void sendEmail(EMAIL_CONTEXT emailContext);
}
