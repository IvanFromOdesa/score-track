package com.teamk.scoretrack.module.security.auth.domain;

import com.teamk.scoretrack.module.commons.base.domain.Identifier;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import com.teamk.scoretrack.module.security.oauth2.external.ExternalAuthentication;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationLock;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.token.util.UUIDUtils;
import com.teamk.scoretrack.module.security.track.domain.AuthenticationTrackingData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Describes the authentication, that is derived from Session, for a particular user.
 * Instances of this class correspond to the db entities.
 * This is used for authentication / authorization flows and anywhere where the user context is required.
 * It is a core db entity that contains sensitive user information, any remove operations for {@link User}
 * should be addressed to objects of this class instead.
 */
// TODO: separate tables for emails and phone numbers to store data more efficiently
@Entity
@Table(name = "authentication")
@EntityListeners({ AuditingEntityListener.class })
public class AuthenticationBean extends Identifier implements ExtendedUserDetails {
    public static final long AUTH_INACTIVITY = 15;
    private static final long CONFIRMED_LIMIT = 30;
    private static final long EXPIRED_LIMIT = 365;
    public static final String FK_NAME = "auth_fk";
    public static final String MAPPED_BY = "authenticationBean";
    /**
     * This is used instead of exposing db ids for security reasons.
     */
    @Column(unique = true)
    private UUID externalId;
    @Column(unique = true)
    private String loginname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;
    private AuthenticationStatus status;
    @CreationTimestamp
    private Instant createdAt;
    private PasswordStatus ps;
    private AuthenticationType authType;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = MAPPED_BY)
    private User user;
    @LastModifiedDate
    private Instant lastModified;
    private Instant lastConfirmedOn;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @LastModifiedBy
    private AuthenticationBean modifiedBy;
    @OneToOne(mappedBy = MAPPED_BY, cascade = CascadeType.ALL)
    private AuthenticationLock latestFailureUnlock;
    @OneToOne(mappedBy = MAPPED_BY, cascade = CascadeType.ALL)
    private AuthenticationTrackingData trackingData;
    /**
     * Authentication histories are not supposed to be accessed via objects of this class.
     * This is for cascade removal operations.
     */
    @OneToMany(mappedBy = MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AuthenticationHistory> authenticationHistories;
    @OneToMany(mappedBy = MAPPED_BY, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ExternalAuthentication> externalAuthentications;

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthenticationStatus getStatus() {
        return status;
    }

    public void setStatus(AuthenticationStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public PasswordStatus getPs() {
        return ps;
    }

    public void setPs(PasswordStatus ps) {
        this.ps = ps;
    }

    public AuthenticationType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthenticationType authType) {
        this.authType = authType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Instant getLastConfirmedOn() {
        return lastConfirmedOn;
    }

    public void setLastConfirmedOn(Instant lastConfirmedOn) {
        this.lastConfirmedOn = lastConfirmedOn;
    }

    public AuthenticationBean getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(AuthenticationBean modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public AuthenticationLock getLatestFailureUnlock() {
        return latestFailureUnlock;
    }

    public void setLatestFailureUnlock(AuthenticationLock latestFailureUnlock) {
        this.latestFailureUnlock = latestFailureUnlock;
    }

    public AuthenticationTrackingData getTrackingData() {
        return trackingData;
    }

    public void setTrackingData(AuthenticationTrackingData trackingData) {
        this.trackingData = trackingData;
    }

    public static AuthenticationBean getDefault(String loginname, String email) {
        return getDefault(loginname, null, email, AuthenticationStatus.ACTIVATED, AuthenticationType.EXTERNAL_OAUTH);
    }

    public static AuthenticationBean getDefault(String loginname, String hash, String email) {
        return getDefault(loginname, hash, email, AuthenticationStatus.CREATED, AuthenticationType.EMAIL);
    }

    public static AuthenticationBean getDefault(String loginname, String hash, String email,
                                                AuthenticationStatus status, AuthenticationType type) {
        AuthenticationBean authenticationBean = new AuthenticationBean();
        authenticationBean.setExternalId(UUIDUtils.v5(loginname));
        authenticationBean.setLoginname(loginname);
        authenticationBean.setPassword(hash);
        authenticationBean.setEmail(email);
        authenticationBean.setStatus(status);
        authenticationBean.setPs(PasswordStatus.NEW);
        authenticationBean.setAuthType(type);
        return authenticationBean;
    }

    @Override
    public Collection<UserPrivilege> getAuthorities() {
        return getUser().getPrivileges();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return lastLogonAfter(EXPIRED_LIMIT);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBadCredentialsFailurePresent();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled() && !ps.isReset();
    }

    @Override
    public boolean isBadCredentialsFailurePresent() {
        return latestFailureUnlock != null && Instant.now().isBefore(latestFailureUnlock.getUnlockedAt());
    }

    @Override
    public boolean isRecentAuthenticationPresent() {
        return status.isActivated() && lastLogonAfter(AUTH_INACTIVITY);
    }

    @Override
    public boolean isLastConfirmed() {
        return lastConfirmedOn != null && lastConfirmedOn.isAfter(CommonsUtil.fromDays(CONFIRMED_LIMIT));
    }

    /**
     * This is currently used to check if the user activated account from email verification.
     * @return
     */
    @Override
    public boolean isEnabled() {
        return status.isActivated();
    }

    private boolean lastLogonAfter(Long afterDays) {
        return trackingData == null || trackingData.getLastLogOn().isAfter(CommonsUtil.fromDays(afterDays));
    }

    @Override
    public String toString() {
        return "Loginname='" + loginname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", ps=" + ps +
                ", authType=" + authType +
                ", lastLogOn=" + (trackingData == null ? "none" : trackingData.getLastLogOn()) +
                ", lastModified=" + lastModified +
                '}';
    }
}
