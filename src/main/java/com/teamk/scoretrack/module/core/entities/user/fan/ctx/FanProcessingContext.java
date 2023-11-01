package com.teamk.scoretrack.module.core.entities.user.fan.ctx;

import com.teamk.scoretrack.module.core.entities.user.base.ctx.BusinessUserProcessingContext;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;

public class FanProcessingContext extends BusinessUserProcessingContext {
    public FanProcessingContext(AuthenticationBean authenticationBean, ViewershipCreationContext viewershipCreationContext) {
        super(authenticationBean, viewershipCreationContext);
    }

    public static FanProcessingContext getDefault(AuthenticationBean authenticationBean) {
        return new FanProcessingContext(authenticationBean, null);
    }
}
