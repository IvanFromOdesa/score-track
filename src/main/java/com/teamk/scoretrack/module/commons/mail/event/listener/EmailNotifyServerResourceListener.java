package com.teamk.scoretrack.module.commons.mail.event.listener;

import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.commons.mail.EmailPrepareService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.EmailNotifyEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;
import java.util.function.Consumer;

/**
 * Builds notification emails from server resources via {@link com.teamk.scoretrack.module.commons.mail.EmailPrepareService}
 * and {@link com.teamk.scoretrack.module.commons.i18n.service.TranslatorService}
 */
public abstract class EmailNotifyServerResourceListener<EMAIL_CONTEXT extends NotificationEmail, EVENT extends EmailNotifyEvent<EMAIL_CONTEXT>> extends EmailNotifyListener<EMAIL_CONTEXT, EVENT> {
    @Autowired
    protected EmailPrepareService emailPrepareService;
    protected TranslatorService translatorService;

    protected void sendNotificationEmail(EMAIL_CONTEXT email, Locale locale, Object... emailPlaceholders) {
        sendNotificationEmail(email, locale, null, emailPlaceholders);
    }

    protected void sendNotificationEmail(EMAIL_CONTEXT email, Locale locale, Consumer<EMAIL_CONTEXT> emailConsumer, Object... emailPlaceholders) {
        EMAIL_CONTEXT preparedEmail = emailPrepareService.prepareEmail(email, translatorService, locale, emailPlaceholders);
        if (emailConsumer != null) {
            emailConsumer.accept(preparedEmail);
        }
        emailService.sendEmail(email);
    }

    protected abstract void setTranslatorService(TranslatorService translatorService);
}
