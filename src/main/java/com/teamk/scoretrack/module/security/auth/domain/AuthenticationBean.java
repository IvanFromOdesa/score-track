package com.teamk.scoretrack.module.security.auth.domain;

import com.teamk.scoretrack.module.commons.base.domain.Identifier;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.security.handler.error.authfailure.domain.AuthenticationCredentialsFailure;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;

/**
 * DB raw user getModel
 */
@Entity
@Table(name = "authentication")
@EntityListeners({ AuditingEntityListener.class })
public class AuthenticationBean extends Identifier implements ExtendedUserDetails {
    private static final long ENABLED_LIMIT = 30;
    private static final long EXPIRED_LIMIT = 365;
    public static final String FK_NAME = "auth_fk";
    @Column(unique = true)
    private String loginname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;
    @Enumerated(EnumType.STRING)
    private AuthenticationStatus status;
    @CreationTimestamp
    private Instant createdAt;
    private PasswordStatus ps;
    private AuthenticationType authType;
    private Instant lastLogOn;
    @OneToOne(mappedBy = "authentication", fetch = FetchType.LAZY)
    private User user;
    @LastModifiedDate
    private Instant lastModified;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @LastModifiedBy
    private AuthenticationBean modifiedBy;
    @Formula(AuthenticationCredentialsFailure.LATEST_FAILURE)
    private Instant latestFailureUnlock;

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

    public Instant getLastLogOn() {
        return lastLogOn;
    }

    public void setLastLogOn(Instant lastLogOn) {
        this.lastLogOn = lastLogOn;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public AuthenticationBean getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(AuthenticationBean modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getLatestFailureUnlock() {
        return latestFailureUnlock;
    }

    public static AuthenticationBean getDefault(String loginname, String hash, String email) {
        AuthenticationBean authenticationBean = new AuthenticationBean();
        authenticationBean.setLoginname(loginname);
        authenticationBean.setPassword(hash);
        authenticationBean.setEmail(email);
        authenticationBean.setStatus(AuthenticationStatus.CREATED);
        authenticationBean.setPs(PasswordStatus.NEW);
        authenticationBean.setAuthType(AuthenticationType.EMAIL);
        return authenticationBean;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(getUser().getPrivileges()).map(SimpleGrantedAuthority::new).toList();
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
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled() && !ps.isReset();
    }

    @Override
    public boolean isBadCredentialsFailurePresent() {
        return latestFailureUnlock != null && Instant.now().isBefore(latestFailureUnlock);
    }

    @Override
    public boolean isEnabled() {
        return status.isActivated() && lastLogonAfter(ENABLED_LIMIT);
    }

    private boolean lastLogonAfter(Long afterDays) {
        return lastLogOn == null || lastLogOn.isAfter(Instant.from(Instant.now().minus(Duration.ofDays(afterDays))));
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
                ", lastLogOn=" + lastLogOn +
                ", lastModified=" + lastModified +
                '}';
    }
}
