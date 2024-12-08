package com.teamk.scoretrack.module.commons.io.mixin;

import org.springframework.stereotype.Component;

@Component
public class LongMixinProvider implements IMixinAware<Long> {
    @Override
    public Class<Long> getTargetClass() {
        return Long.class;
    }
}
