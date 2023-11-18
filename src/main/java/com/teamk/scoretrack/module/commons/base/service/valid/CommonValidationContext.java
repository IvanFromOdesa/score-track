package com.teamk.scoretrack.module.commons.base.service.valid;

import com.teamk.scoretrack.module.commons.other.ErrorMap;

public class CommonValidationContext {
    protected final ErrorMap errorMap;

    public CommonValidationContext() {
        this.errorMap = new ErrorMap();
    }

    public ErrorMap getErrorMap() {
        return errorMap;
    }
}
