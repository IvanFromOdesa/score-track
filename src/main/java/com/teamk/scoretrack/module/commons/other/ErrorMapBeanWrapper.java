package com.teamk.scoretrack.module.commons.other;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * ErrorMap JSON-wrapped response for REST APIs.
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ErrorMapBeanWrapper {
    private ErrorMap errorMap;

    public ErrorMapBeanWrapper() {
        this.errorMap = new ErrorMap();
    }

    public ErrorMap getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(ErrorMap errorMap) {
        this.errorMap = errorMap;
    }
}
