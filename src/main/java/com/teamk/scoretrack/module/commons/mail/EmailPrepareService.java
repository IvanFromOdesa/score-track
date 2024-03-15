package com.teamk.scoretrack.module.commons.mail;

import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailPrepareService {
    public NotificationEmail prepareEmail(String recipient, TranslatorService translatorService, Object... bodyPlaceholders) {
        return prepareEmail(recipient, translatorService, LocaleContextHolder.getLocale(), bodyPlaceholders);
    }

    public NotificationEmail prepareEmail(String recipient, TranslatorService translatorService, Locale locale, Object... bodyPlaceholders) {
        NotificationEmail email = new NotificationEmail();
        email.setRecipient(recipient);
        return fillEmail(translatorService, locale, email, bodyPlaceholders);
    }

    public <EMAIL_CONTEXT extends NotificationEmail> EMAIL_CONTEXT prepareEmail(EMAIL_CONTEXT email, TranslatorService translatorService, Locale locale, Object... bodyPlaceholders) {
        return fillEmail(translatorService, locale, email, bodyPlaceholders);
    }

    private static <EMAIL_CONTEXT extends NotificationEmail> EMAIL_CONTEXT fillEmail(TranslatorService translatorService, Locale locale, EMAIL_CONTEXT email, Object[] bodyPlaceholders) {
        email.setSubject(translatorService.getMessage("mail.subject", locale));
        email.setTitle(translatorService.getMessage("mail.title", locale));
        email.setMessage(translatorService.getMessage("mail.body", locale, bodyPlaceholders));
        return email;
    }
}
