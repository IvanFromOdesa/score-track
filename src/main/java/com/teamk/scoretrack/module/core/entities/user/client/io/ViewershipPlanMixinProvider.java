package com.teamk.scoretrack.module.core.entities.user.client.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ViewershipPlan;
import org.springframework.stereotype.Component;

@Component
public class ViewershipPlanMixinProvider implements IMixinAware<ViewershipPlan> {
    @Override
    public Class<ViewershipPlan> getTargetClass() {
        return ViewershipPlan.class;
    }
}
