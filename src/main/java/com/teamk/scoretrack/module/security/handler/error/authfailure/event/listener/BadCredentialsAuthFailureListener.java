package com.teamk.scoretrack.module.security.handler.error.authfailure.event.listener;

import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.listener.EmailNotifyServerResourceListener;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.BadCredentialsAuthenticationAttempt;
import com.teamk.scoretrack.module.security.handler.error.authfailure.event.BadCredentialsAuthFailureEvent;
import com.teamk.scoretrack.module.security.handler.error.authfailure.service.AuthenticationLockService;
import com.teamk.scoretrack.module.security.handler.error.i18n.AuthenticationErrorTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class BadCredentialsAuthFailureListener extends EmailNotifyServerResourceListener<NotificationEmail, BadCredentialsAuthFailureEvent> {
    private final AuthenticationEntityService authenticationEntityService;
    private final AuthenticationLockService authenticationLockService;

    @Autowired
    public BadCredentialsAuthFailureListener(AuthenticationEntityService authenticationEntityService,
                                             AuthenticationLockService authenticationLockService) {
        this.authenticationEntityService = authenticationEntityService;
        this.authenticationLockService = authenticationLockService;
    }

    @Override
    @EventListener
    @Async("fixedThreadPool")
    public void handleEvent(BadCredentialsAuthFailureEvent event) {
        AuthenticationBean authenticationBean = (AuthenticationBean) authenticationEntityService.loadUserByUsername(event.getAuthentication());
        boolean locked = authenticationLockService.lock(authenticationBean, BadCredentialsAuthenticationAttempt.LOCK_DURATION);
        sendNotificationEmail(event, authenticationBean, locked);
    }

    private void sendNotificationEmail(BadCredentialsAuthFailureEvent event, AuthenticationBean authenticationBean, boolean locked) {
        NotificationEmail email = event.getNotificationEmail();
        email.setRecipient(authenticationBean.getEmail());
        Language preferredLang = authenticationBean.getUser().getPreferredLang();
        Locale locale = preferredLang.getLocale();
        sendNotificationEmail(email, locale, placeholders(event, preferredLang.getDtFormatter(), locked ? translatorService.getMessage("mail.optional", locale, AuthenticationBean.AUTH_INACTIVITY) : ""));
    }

    private Object[] placeholders(BadCredentialsAuthFailureEvent event, DateTimeFormatter dtFormatter, String accountLock) {
        return new Object[] {
                event.getAuthentication(),
                accountLock,
                dtFormatter.format(event.getIssuedAt()),
                event.getAttemptedGeo().location(),
                event.getAttemptedIp(),
                event.getAttemptedDevice(),
                event.getRecoveryLink(),
                issuer
        };
    }

    @Override
    @Autowired
    protected void setEmailService(IEmailService<NotificationEmail> emailService) {
        this.emailService = emailService;
    }

    @Override
    @Autowired
    protected void setTranslatorService(@Qualifier(AuthenticationErrorTranslatorService.NAME) TranslatorService translatorService) {
        this.translatorService = translatorService;
    }
}
