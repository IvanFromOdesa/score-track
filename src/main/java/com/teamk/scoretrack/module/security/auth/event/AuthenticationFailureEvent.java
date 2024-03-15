package com.teamk.scoretrack.module.security.auth.event;

import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.event.EmailNotifyEvent;
import com.teamk.scoretrack.module.security.geo.service.GeoResponse;
import com.teamk.scoretrack.module.security.util.DeviceMetadataResolver;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

public abstract class AuthenticationFailureEvent<AUTHENTICATION_TYPE> extends EmailNotifyEvent<NotificationEmail> {
    protected AUTHENTICATION_TYPE authentication;
    protected GeoResponse attemptedGeo;
    protected String attemptedDevice;
    protected String attemptedIp;
    protected String recoveryLink;

    protected AuthenticationFailureEvent(NotificationEmail notificationEmail, Instant issuedAt, Cause cause) {
        super(notificationEmail, issuedAt, cause);
    }

    /**
     * WARNING: {@link #notificationEmail} must be set separately
     * @param issuedAt
     */
    protected AuthenticationFailureEvent(Instant issuedAt, Cause cause) {
        super(NotificationEmail.NULL_SAFE, issuedAt, cause);
    }

    public void setDefaults(GeoResponse geoResponse, String ip, HttpServletRequest request) {
        this.setAttemptedGeo(geoResponse);
        this.setAttemptedDevice(DeviceMetadataResolver.getDeviceMD(request));
        this.setAttemptedIp(ip);
        // TODO: set recovery link
        this.setRecoveryLink(HttpUtil.getBaseUrl(request).concat(""));
    }

    public AUTHENTICATION_TYPE getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AUTHENTICATION_TYPE authentication) {
        this.authentication = authentication;
    }

    public GeoResponse getAttemptedGeo() {
        return attemptedGeo;
    }

    public void setAttemptedGeo(GeoResponse attemptedGeo) {
        this.attemptedGeo = attemptedGeo;
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

    public String getRecoveryLink() {
        return recoveryLink;
    }

    public void setRecoveryLink(String recoveryLink) {
        this.recoveryLink = recoveryLink;
    }
}
