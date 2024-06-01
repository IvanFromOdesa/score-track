package com.teamk.scoretrack.module.core.api.commons.base;

public abstract class UiWrapperResponse {
    private final String value;
    private final String uiText;

    protected UiWrapperResponse(String value, String uiText) {
        this.value = value;
        this.uiText = uiText;
    }

    public String getValue() {
        return value;
    }

    public String getUiText() {
        return uiText;
    }
}
