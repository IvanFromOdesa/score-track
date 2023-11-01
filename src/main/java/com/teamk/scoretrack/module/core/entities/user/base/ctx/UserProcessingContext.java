package com.teamk.scoretrack.module.core.entities.user.base.ctx;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;

public abstract class UserProcessingContext {
    private final AuthenticationBean authenticationBean;

    public UserProcessingContext(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public AuthenticationBean authenticationBean() {
        return authenticationBean;
    }
}
