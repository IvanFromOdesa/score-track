package com.teamk.scoretrack.module.core.entities.user.fan.ctx;

import com.teamk.scoretrack.module.core.entities.user.client.ctx.ClientUserProcessingContext;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;

public class FanProcessingContext extends ClientUserProcessingContext {
    public FanProcessingContext(AuthenticationBean authenticationBean, ViewershipCreationContext viewershipCreationContext) {
        super(authenticationBean, viewershipCreationContext);
    }

    public static FanProcessingContext getDefault(AuthenticationBean authenticationBean) {
        return new FanProcessingContext(authenticationBean, null);
    }
}
