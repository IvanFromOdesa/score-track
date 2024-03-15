package com.teamk.scoretrack.module.commons.exception;

// TODO
public class BaseEntityNotFoundException extends ServerException {
    private final String strCause;
    private final boolean isRequest;

    public BaseEntityNotFoundException(String message, String strCause, boolean isRequest) {
        super(message);
        this.strCause = strCause;
        this.isRequest = isRequest;
    }

    public BaseEntityNotFoundException(String message, boolean isRequest) {
        super(message);
        this.strCause = "";
        this.isRequest = isRequest;
    }

    public String getStrCause() {
        return strCause;
    }

    public boolean isRequest() {
        return isRequest;
    }
}
