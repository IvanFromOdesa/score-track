package com.teamk.scoretrack.module.security.geo.event.listener;

import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.listener.EmailNotifyServerResourceListener;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.geo.domain.LocationHistory;
import com.teamk.scoretrack.module.security.geo.event.UnknownLocationEvent;
import com.teamk.scoretrack.module.security.geo.i18n.GeoTranslatorService;
import com.teamk.scoretrack.module.security.geo.service.GeoResponse;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.token.otp.domain.OTPToken;
import com.teamk.scoretrack.module.security.token.otp.service.OTPAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class UnknownLocationListener extends EmailNotifyServerResourceListener<NotificationEmail, UnknownLocationEvent> {
    private final AuthenticationEntityService authenticationService;
    private final OTPAuthService otpAuthService;

    @Autowired
    public UnknownLocationListener(AuthenticationEntityService authenticationService,
                                   OTPAuthService otpAuthService) {
        this.authenticationService = authenticationService;
        this.otpAuthService = otpAuthService;
    }

    @Override
    @EventListener
    @Async("fixedThreadPool")
    public void handleEvent(UnknownLocationEvent event) {
        Long bhId = authenticationService.addAuthHistory(prepareLH(event));
        Language preferredLang = event.getAuthentication().getUser().getPreferredLang();
        sendNotificationEmail(event.getNotificationEmail(), preferredLang.getLocale(), email -> cache(event.getAuthentication().getExternalId().toString(), email), placeholders(event, bhId, preferredLang.getDtFormatter()));
    }

    private LocationHistory prepareLH(UnknownLocationEvent event) {
        LocationHistory lc = new LocationHistory(event.getAuthentication(), AuthenticationHistory.Status.BLOCKED);
        GeoResponse attemptedGeo = event.getAttemptedGeo();
        lc.setCountry(attemptedGeo.country());
        lc.setCity(attemptedGeo.city());
        lc.setLat(attemptedGeo.latitude());
        lc.setLng(attemptedGeo.longitude());
        return lc;
    }

    private Object[] placeholders(UnknownLocationEvent event, Long bhId, DateTimeFormatter dtFormatter) {
        AuthenticationBean authenticationBean = event.getAuthentication();
        return new Object[] {
                authenticationBean.getLoginname(),
                issuer,
                dtFormatter.format(event.getIssuedAt()),
                event.getAttemptedGeo().location(),
                event.getAttemptedIp(),
                event.getAttemptedDevice(),
                otpAuthService.generate(authenticationBean.getExternalId().toString(), bhId),
                TimeUnit.MINUTES.convert(OTPToken.TTL, TimeUnit.SECONDS),
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
    protected void setTranslatorService(@Qualifier(GeoTranslatorService.NAME) TranslatorService translatorService) {
        this.translatorService = translatorService;
    }
}
