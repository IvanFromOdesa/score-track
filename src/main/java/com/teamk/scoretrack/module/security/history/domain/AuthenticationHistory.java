package com.teamk.scoretrack.module.security.history.domain;

import com.teamk.scoretrack.module.commons.domain.Identifier;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AuthenticationHistory extends Identifier {
    @ManyToOne
    @JoinColumn(name = "auth_fk")
    private AuthenticationBean authenticationBean;
    @CreationTimestamp
    private Instant issuedAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    public AuthenticationHistory(AuthenticationBean authenticationBean, Status status) {
        this.authenticationBean = authenticationBean;
        this.status = status;
    }

    protected AuthenticationHistory() {
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AuthenticationBlockedStatus getBlockedStatusType() {
        return AuthenticationBlockedStatus.DEFAULT;
    }

    public enum Status {
        TRUSTED,
        BLOCKED;

        public boolean isTrusted() {
            return this == TRUSTED;
        }

        public boolean isBlocked() {
            return this == BLOCKED;
        }
    }
}
