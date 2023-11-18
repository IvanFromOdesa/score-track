package com.teamk.scoretrack.module.security.geo.event.listener;

import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.listener.EmailNotifyListener;
import com.teamk.scoretrack.module.commons.mail.resend.domain.ResendNotificationEmail;
import com.teamk.scoretrack.module.commons.mail.resend.service.ResendNotificationEmailService;
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
    private final ResendNotificationEmailService resendNotificationEmailService;
    @Value("${spring.application.name}")
    private String issuer;

    @Autowired
    public UnknownLocationListener(AuthenticationEntityService authenticationService,
                                   OTPAuthService otpAuthService,
                                   GeoTranslatorService translatorService,
                                   ResendNotificationEmailService resendNotificationEmailService) {
        this.authenticationService = authenticationService;
        this.otpAuthService = otpAuthService;
        this.translatorService = translatorService;
        this.resendNotificationEmailService = resendNotificationEmailService;
    }

    @Override
    @EventListener
    @Async("fixedThreadPool")
    public void handleEvent(UnknownLocationEvent event) {
        Long bhId = authenticationService.addAuthHistory(prepareLH(event));
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
        cache(event.getAuthenticationBean().getId().toString(), email);
        emailService.sendEmail(email);
    }

    private void cache(String id, NotificationEmail email) {
        ResendNotificationEmail ctx = new ResendNotificationEmail(email, id);
        ctx.setAttempt(ctx.getAttempt() + 1);
        resendNotificationEmailService.cache(ctx);
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
                event.getRecoveryLink(),
                issuer
        };
    }

    private String formatDT(Instant issuedAt, DateTimeFormatter dtFormatter) {
        return dtFormatter.format(issuedAt);
    }

    private String formatCC(String country, String city) {
        return city != null ? country.concat(", ").concat(city) : country;
    }

    @Override
    @Autowired
    protected void setEmailService(IEmailService<NotificationEmail> emailService) {
        this.emailService = emailService;
    }
}
