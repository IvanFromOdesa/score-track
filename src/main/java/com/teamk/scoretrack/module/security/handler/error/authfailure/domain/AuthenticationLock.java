package com.teamk.scoretrack.module.security.handler.error.authfailure.domain;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationIdentifier;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = AuthenticationLock.TABLE_NAME)
public class AuthenticationLock extends AuthenticationIdentifier {
    public static final String TABLE_NAME = "authentication_lock";
    private Instant unlockedAt;

    public Instant getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(Instant unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    @Override
    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        if (authenticationBean.getLatestFailureUnlock() == null) {
            authenticationBean.setLatestFailureUnlock(this);
        }
        super.setAuthenticationBean(authenticationBean);
    }
}
