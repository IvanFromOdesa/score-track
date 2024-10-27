package com.teamk.scoretrack.module.security.oauth2.external;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.base.domain.IdAware;
import com.teamk.scoretrack.module.commons.util.enums.EnumUtils;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
public class ExternalAuthentication implements IdAware<ExternalAuthentication.ExternalAuthenticationId> {
    @EmbeddedId
    private ExternalAuthenticationId id;
    @ManyToOne
    @MapsId("authenticationBeanId")
    private AuthenticationBean authenticationBean;
    @CreationTimestamp
    private Instant issuedAt;
    private String externalId;

    public ExternalAuthenticationId getId() {
        return id;
    }

    public void setId(ExternalAuthenticationId id) {
        this.id = id;
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Embeddable
    public static class ExternalAuthenticationId implements Serializable {
        private Long authenticationBeanId;
        private Type type;

        public Long getAuthenticationBeanId() {
            return authenticationBeanId;
        }

        public void setAuthenticationBeanId(Long authFk) {
            this.authenticationBeanId = authFk;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ExternalAuthenticationId that = (ExternalAuthenticationId) o;
            return Objects.equals(authenticationBeanId, that.authenticationBeanId) && type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(authenticationBeanId, type);
        }
    }

    public enum Type implements IEnumConvert<Integer, Type> {
        GOOGLE_OAUTH_2(1), UNDEFINED(CODE_UNDEFINED);

        public static final BiMap<Integer, Type> LOOKUP_MAP = EnumUtils.createLookup(Type.class);

        private final int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        @Override
        public BiMap<Integer, Type> getLookup() {
            return LOOKUP_MAP;
        }

        @Override
        public Type getDefault() {
            return UNDEFINED;
        }

        @Override
        public Integer getKey() {
            return code;
        }
    }
}
