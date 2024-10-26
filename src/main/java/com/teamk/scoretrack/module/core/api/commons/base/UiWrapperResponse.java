package com.teamk.scoretrack.module.core.api.commons.base;

public abstract class UiWrapperResponse<V> {
    private final V value;
    private final String uiText;

    protected UiWrapperResponse(V value, String uiText) {
        this.value = value;
        this.uiText = uiText;
    }

    public V getValue() {
        return value;
    }

    public String getUiText() {
        return uiText;
    }
}
