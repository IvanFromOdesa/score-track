package com.teamk.scoretrack.module.core.entities.user.base.event;

import com.teamk.scoretrack.module.core.entities.user.base.ctx.UserProcessingContext;

public abstract class UserProcessingEvent<CTX extends UserProcessingContext> {
    private final CTX ctx;
    private final OperationType type;

    protected UserProcessingEvent(CTX ctx, OperationType type) {
        this.ctx = ctx;
        this.type = type;
    }

    public CTX getCtx() {
        return ctx;
    }

    public OperationType getType() {
        return type;
    }

    public enum OperationType {
        CREATE, UPDATE, DELETE;

        public boolean isCreate() {
            return this == CREATE;
        }

        public boolean isUpdate() {
            return this == UPDATE;
        }
    }
}
