package com.teamk.scoretrack.module.commons.layout.alert;

/**
 * This should hold all the options for rendering alerts in templates in session.
 */
public class UiAlertDisplayOptions {
    protected static final String ATTRIBUTE_NAME = "modelAlertDisplayOptions";

    private boolean accountActivated;
    private boolean firstLogIn;

    public boolean isAccountActivated() {
        return accountActivated;
    }

    public void setAccountActivated(boolean accountActivated) {
        this.accountActivated = accountActivated;
    }

    public boolean isFirstLogIn() {
        return firstLogIn;
    }

    public void setFirstLogIn(boolean firstLogIn) {
        this.firstLogIn = firstLogIn;
    }
}
