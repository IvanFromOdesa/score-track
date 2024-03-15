package com.teamk.scoretrack.module.security.geo.event;

import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.event.AuthenticationFailureEvent;

import java.time.Instant;

public class UnknownLocationEvent extends AuthenticationFailureEvent<AuthenticationBean> {
    public UnknownLocationEvent(NotificationEmail notificationEmail, Instant issuedAt) {
        super(notificationEmail, issuedAt, Cause.UNKNOWN_LOCATION_AUTH);
    }
}
