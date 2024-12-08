package com.teamk.scoretrack.module.core.entities.user.fan.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import org.springframework.stereotype.Component;

@Component
public class FanMixinProvider implements IMixinAware<Fan> {
    @Override
    public Class<Fan> getTargetClass() {
        return Fan.class;
    }
}
