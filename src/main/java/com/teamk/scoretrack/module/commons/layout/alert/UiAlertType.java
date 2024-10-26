package com.teamk.scoretrack.module.commons.layout.alert;

public enum UiAlertType {
    INFO("Info"), WARNING("Warning"), ERROR("Error");

    private final String name;

    UiAlertType(String name) {
        this.name = "alert" + name;
    }

    public String getName() {
        return name;
    }
}
