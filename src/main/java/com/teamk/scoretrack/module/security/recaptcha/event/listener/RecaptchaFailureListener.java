package com.teamk.scoretrack.module.security.recaptcha.event.listener;

import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.listener.EmailNotifyServerResourceListener;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationWrapper;
import com.teamk.scoretrack.module.security.recaptcha.event.RecaptchaFailureEvent;
import com.teamk.scoretrack.module.security.recaptcha.service.i18n.RecaptchaTranslatorService;
import com.teamk.scoretrack.module.security.token.otp.service.OTPAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class RecaptchaFailureListener extends EmailNotifyServerResourceListener<NotificationEmail, RecaptchaFailureEvent> {
    private final OTPAuthService otpAuthService;

    @Autowired
    public RecaptchaFailureListener(OTPAuthService otpAuthService) {
        this.otpAuthService = otpAuthService;
    }

    // TODO: storing recaptcha scores metrics calling external service
    @Override
    @EventListener
    @Async("fixedThreadPool")
    public void handleEvent(RecaptchaFailureEvent event) {
        Language preferredLang = event.getAuthentication().preferredLanguage();
        sendNotificationEmail(event.getNotificationEmail(), preferredLang.getLocale(), email -> cache(event.getAuthentication().externalId().toString(), email), placeholders(event, preferredLang.getDtFormatter()));
    }

    private Object[] placeholders(RecaptchaFailureEvent event, DateTimeFormatter dtFormatter) {
        AuthenticationWrapper authentication = event.getAuthentication();
        return new Object[] {
                authentication.loginname(),
                dtFormatter.format(event.getIssuedAt()),
                event.getAttemptedIp(),
                event.getAttemptedDevice(),
                otpAuthService.generate(authentication.externalId().toString(), null),
                event.getRecoveryLink(),
                supportEmail,
                issuer
        };
    }

    @Override
    @Autowired
    protected void setEmailService(IEmailService<NotificationEmail> emailContext) {
        this.emailService = emailContext;
    }

    @Override
    @Autowired
    protected void setTranslatorService(@Qualifier(RecaptchaTranslatorService.NAME) TranslatorService translatorService) {
        this.translatorService = translatorService;
    }
}
