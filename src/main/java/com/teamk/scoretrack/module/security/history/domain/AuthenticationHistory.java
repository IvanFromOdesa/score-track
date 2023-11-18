package com.teamk.scoretrack.module.security.history.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.teamk.scoretrack.module.commons.base.domain.Identifier;
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
    @JoinColumn(name = AuthenticationBean.FK_NAME)
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

    public enum Status {
        TRUSTED(0),
        BLOCKED(1),
        UNDEFINED(2);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        @JsonValue
        public int getCode() {
            return code;
        }

        @JsonCreator
        public static Status deserialize(int code) {
            for (Status status : Status.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            return UNDEFINED;
        }

        public boolean isTrusted() {
            return this == TRUSTED;
        }

        public boolean isBlocked() {
            return this == BLOCKED;
        }

        public boolean isUndefined() {
            return this == UNDEFINED;
        }
    }
}
