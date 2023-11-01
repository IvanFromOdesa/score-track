package com.teamk.scoretrack.module.security.token.otp.domain;

import com.teamk.scoretrack.module.commons.domain.IdAware;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;

import java.io.Serializable;
import java.time.Instant;

/**
 * @param otp randomly generated recovery code
 * @param id {@link AuthenticationBean#getId()}
 * @param created creation time of token
 */
public record OTPToken(String otp, String id, Long bhId, Instant created) implements IdAware<String>, Serializable {
    public static final long TTL = 1800;

    @Override
    public String getId() {
        return id();
    }
}
