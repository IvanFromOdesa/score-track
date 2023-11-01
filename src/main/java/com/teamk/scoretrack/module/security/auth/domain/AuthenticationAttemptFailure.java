package com.teamk.scoretrack.module.security.auth.domain;

import com.teamk.scoretrack.module.commons.domain.Identifier;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
public class AuthenticationAttemptFailure extends Identifier {
    public static final int MAX_FAILED_ATTEMPTS = 5;
    private int attempts;
    @UpdateTimestamp
    private Instant latestAttempt;

    /**
     * The unlockOn represents the datetime when the account becomes non-locked.<br>
     * The locked will be set to false, when the user successfully logs in after the unlockOn datetime.
     */
    private boolean locked;
    private Instant unlockOn;
    @ManyToOne
    @JoinColumn(name = "auth_fk")
    private AuthenticationBean authentication;

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Instant getLatestAttempt() {
        return latestAttempt;
    }

    public void setLatestAttempt(Instant latestAttempt) {
        this.latestAttempt = latestAttempt;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Instant getUnlockOn() {
        return unlockOn;
    }

    public void setUnlockOn(Instant unlockOn) {
        this.unlockOn = unlockOn;
    }

    public AuthenticationBean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationBean authentication) {
        this.authentication = authentication;
    }
}
