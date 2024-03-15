package com.teamk.scoretrack.module.security.commons;

import org.springframework.security.core.AuthenticationException;

/**
 * {@link org.springframework.http.HttpStatus#FORBIDDEN} exceptions
 */
public abstract class ForbiddenResponseException extends AuthenticationException {
    public ForbiddenResponseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ForbiddenResponseException(String msg) {
        super(msg);
    }
}
