package com.teamk.scoretrack.module.security.auth.domain;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

/**
 * Maps the child class to {@link AuthenticationBean}.
 * {@link #id} is the shared primary key for authentication table (id).
 */
@MappedSuperclass
public class AuthenticationIdentifier implements IdAware<Long> {
    public static final String FK_NAME = "auth_id";
    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = FK_NAME)
    private AuthenticationBean authenticationBean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.setId(authenticationBean.getId());
        this.authenticationBean = authenticationBean;
    }
}
