package com.teamk.scoretrack.module.core.entities.user.fan.event;

import com.teamk.scoretrack.module.core.entities.user.base.event.UserProcessingEvent;
import com.teamk.scoretrack.module.core.entities.user.fan.ctx.FanProcessingContext;

public class FanProcessingEvent extends UserProcessingEvent<FanProcessingContext> {
    public FanProcessingEvent(FanProcessingContext ctx, OperationType type) {
        super(ctx, type);
    }
}
