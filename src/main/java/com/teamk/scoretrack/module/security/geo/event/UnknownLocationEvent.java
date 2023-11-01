package com.teamk.scoretrack.module.security.geo.event;

import com.teamk.scoretrack.module.commons.service.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.service.mail.event.EmailNotifyEvent;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;

import java.time.Instant;

public class UnknownLocationEvent extends EmailNotifyEvent<NotificationEmail> {
    private AuthenticationBean authenticationBean;
    private String attemptedCountry;
    private String attemptedCity;
    private String attemptedDevice;
    private String attemptedIp;

    public UnknownLocationEvent(NotificationEmail notificationEmail, Instant issuedAt) {
        super(notificationEmail, issuedAt, Cause.UNKNOWN_LOCATION_AUTH);
    }

    public String getAttemptedCountry() {
        return attemptedCountry;
    }

    public void setAttemptedCountry(String attemptedCountry) {
        this.attemptedCountry = attemptedCountry;
    }

    public String getAttemptedCity() {
        return attemptedCity;
    }

    public void setAttemptedCity(String attemptedCity) {
        this.attemptedCity = attemptedCity;
    }

    public String getAttemptedDevice() {
        return attemptedDevice;
    }

    public void setAttemptedDevice(String attemptedDevice) {
        this.attemptedDevice = attemptedDevice;
    }

    public String getAttemptedIp() {
        return attemptedIp;
    }

    public void setAttemptedIp(String attemptedIp) {
        this.attemptedIp = attemptedIp;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }
}
