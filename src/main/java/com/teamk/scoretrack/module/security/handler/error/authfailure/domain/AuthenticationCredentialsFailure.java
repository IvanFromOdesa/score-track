package com.teamk.scoretrack.module.security.handler.error.authfailure.domain;

import com.teamk.scoretrack.module.commons.base.domain.Identifier;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

import static com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean.FK_NAME;

@Entity
@Table(name = AuthenticationCredentialsFailure.TABLE_NAME)
public class AuthenticationCredentialsFailure extends Identifier {
    public static final String TABLE_NAME = "authentication_failure";
    public static final long LOCK_DURATION = 60 * 60 * 24; // In seconds
    /**
     * This query is rdbms-independent (implementing sql-92 standard).
     */
    public static final String LATEST_FAILURE = "(select max(f1.unlocked_at) from " + TABLE_NAME + " f1 where f1." + FK_NAME + " = id)";
    private Instant unlockedAt;
    @ManyToOne
    @JoinColumn(name = FK_NAME)
    private AuthenticationBean authentication;

    public Instant getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(Instant unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    public AuthenticationBean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationBean authentication) {
        this.authentication = authentication;
    }
}
