package com.teamk.scoretrack.module.security.track.domain;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationIdentifier;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = AuthenticationTrackingData.TABLE_NAME)
public class AuthenticationTrackingData extends AuthenticationIdentifier {
    public static final String TABLE_NAME = "tracking_data";
    private Instant lastLogOn;
    private int totalLogIn;
    /**
     * Denormalized, but does not require storing all log ins as separate records with many-to-one
     */
    private int consecutiveLogIn;

    public Instant getLastLogOn() {
        return lastLogOn;
    }

    public void setLastLogOn(Instant lastLogOn) {
        this.lastLogOn = lastLogOn;
    }

    public int getTotalLogIn() {
        return totalLogIn;
    }

    public void setTotalLogIn(int totalLogIn) {
        this.totalLogIn = totalLogIn;
    }

    public int getConsecutiveLogIn() {
        return consecutiveLogIn;
    }

    public void setConsecutiveLogIn(int consecutiveLogIn) {
        this.consecutiveLogIn = consecutiveLogIn;
    }

    /**
     * Fix for null identifier Hibernate 6.2.6.
     * @param authenticationBean
     */
    @Override
    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        if (authenticationBean.getTrackingData() == null) {
            authenticationBean.setTrackingData(this);
        }
        super.setAuthenticationBean(authenticationBean);
    }
}
