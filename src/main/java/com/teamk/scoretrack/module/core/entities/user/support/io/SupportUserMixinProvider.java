package com.teamk.scoretrack.module.core.entities.user.support.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import com.teamk.scoretrack.module.core.entities.user.support.domain.SupportUser;
import org.springframework.stereotype.Component;

@Component
public class SupportUserMixinProvider implements IMixinAware<SupportUser> {
    @Override
    public Class<SupportUser> getTargetClass() {
        return SupportUser.class;
    }
}
