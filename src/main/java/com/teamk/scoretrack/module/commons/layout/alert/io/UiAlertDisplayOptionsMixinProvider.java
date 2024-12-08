package com.teamk.scoretrack.module.commons.layout.alert.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptions;
import org.springframework.stereotype.Component;

@Component
public class UiAlertDisplayOptionsMixinProvider implements IMixinAware<UiAlertDisplayOptions> {
    @Override
    public Class<UiAlertDisplayOptions> getTargetClass() {
        return UiAlertDisplayOptions.class;
    }
}
