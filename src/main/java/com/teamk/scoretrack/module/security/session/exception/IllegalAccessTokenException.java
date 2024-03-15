package com.teamk.scoretrack.module.security.session.exception;

import com.teamk.scoretrack.module.security.commons.ForbiddenResponseException;

public class IllegalAccessTokenException extends ForbiddenResponseException {
    public IllegalAccessTokenException(String msg) {
        super(msg);
    }
}
