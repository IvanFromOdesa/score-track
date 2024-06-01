package com.teamk.scoretrack.module.commons.exception;

import com.teamk.scoretrack.module.commons.other.ErrorMap;

public class BaseErrorMapException extends ServerException {
    private ErrorMap errorMap;

    public BaseErrorMapException(String message) {
        super(message);
    }

    public BaseErrorMapException(ErrorMap errorMap) {
        super(errorMap.getErrors().toString());
        this.errorMap = errorMap;
    }

    public ErrorMap getErrorMap() {
        return errorMap;
    }
}
