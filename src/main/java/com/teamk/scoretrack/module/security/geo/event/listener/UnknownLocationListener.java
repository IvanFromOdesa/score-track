package com.teamk.scoretrack.module.security.geo.event.listener;

import com.teamk.scoretrack.module.commons.service.BaseTransactionManager;
import com.teamk.scoretrack.module.commons.service.mail.IEmailService;
import com.teamk.scoretrack.module.commons.service.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.service.mail.event.listener.EmailNotifyListener;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.geo.domain.LocationHistory;
import com.teamk.scoretrack.module.security.geo.event.UnknownLocationEvent;
import com.teamk.scoretrack.module.security.geo.i18n.GeoTranslatorService;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.token.otp.domain.OTPToken;
import com.teamk.scoretrack.module.security.token.otp.service.OTPAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class UnknownLocationListener extends EmailNotifyListener<NotificationEmail, UnknownLocationEvent> {
    private final AuthenticationEntityService authenticationService;
    private final OTPAuthService otpAuthService;
    private final GeoTranslatorService translatorService;
    private final BaseTransactionManager transactionManager;
    @Value("${spring.application.name}")
    private String issuer;

    @Autowired
    public UnknownLocationListener(AuthenticationEntityService authenticationService,
                                   OTPAuthService otpAuthService,
                                   GeoTranslatorService translatorService,
                                   BaseTransactionManager transactionManager) {
        this.authenticationService = authenticationService;
        this.otpAuthService = otpAuthService;
        this.translatorService = translatorService;
        this.transactionManager = transactionManager;
    }

    @Override
    @EventListener
    @Async("fixedThreadPool")
    public void handleEvent(UnknownLocationEvent event) {
        Long bhId = transactionManager.doInNewTransaction(status -> authenticationService.addHistory(prepareLH(event)));
        Language preferredLang = event.getAuthenticationBean().getUser().getPreferredLang();
        sendNotificationEmail(event, bhId, preferredLang.getLocale(), preferredLang.getDtFormatter());
    }

    private LocationHistory prepareLH(UnknownLocationEvent event) {
        LocationHistory lc = new LocationHistory(event.getAuthenticationBean(), AuthenticationHistory.Status.BLOCKED);
        lc.setIpHash(LocationHistory.hashed(event.getAttemptedIp()));
        return lc;
    }

    private void sendNotificationEmail(UnknownLocationEvent event, Long bhId, Locale locale, DateTimeFormatter dtFormatter) {
        NotificationEmail email = event.getNotificationEmail();
        email.setSubject(translatorService.getMessage("mail.subject", locale));
        email.setTitle(translatorService.getMessage("mail.title", locale));
        email.setMessage(translatorService.getMessage("mail.body", locale, placeholders(event, bhId, dtFormatter)));
        emailService.sendEmail(email);
    }

    private Object[] placeholders(UnknownLocationEvent event, Long bhId, DateTimeFormatter dtFormatter) {
        AuthenticationBean authenticationBean = event.getAuthenticationBean();
        return new Object[] {
                authenticationBean.getLoginname(),
                issuer,
                formatDT(event.getIssuedAt(), dtFormatter),
                formatCC(event.getAttemptedCountry(), event.getAttemptedCity()),
                event.getAttemptedIp(),
                event.getAttemptedDevice(),
                otpAuthService.generate(authenticationBean.getId().toString(), bhId),
                TimeUnit.MINUTES.convert(OTPToken.TTL, TimeUnit.SECONDS),
                issuer
        };
    }

    private String formatDT(Instant issuedAt, DateTimeFormatter dtFormatter) {
        return dtFormatter.format(issuedAt);
    }

    private String formatCC(String country, String city) {
        return country.concat(", ").concat(city);
    }

    @Override
    @Autowired
    protected void setEmailService(IEmailService<NotificationEmail> emailService) {
        this.emailService = emailService;
    }
}
