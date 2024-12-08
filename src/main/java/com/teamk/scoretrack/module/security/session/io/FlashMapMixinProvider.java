package com.teamk.scoretrack.module.security.session.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;

@Component
public class FlashMapMixinProvider implements IMixinAware<FlashMap> {
    @Override
    public Class<FlashMap> getTargetClass() {
        return FlashMap.class;
    }
}
