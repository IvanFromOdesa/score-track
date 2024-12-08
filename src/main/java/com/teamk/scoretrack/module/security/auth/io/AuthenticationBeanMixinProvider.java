package com.teamk.scoretrack.module.security.auth.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationBeanMixinProvider implements IMixinAware<AuthenticationBean> {
    @Override
    public Class<AuthenticationBean> getTargetClass() {
        return AuthenticationBean.class;
    }
}
