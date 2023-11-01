package com.teamk.scoretrack.module.commons.exception;

/**
 * General 500 error
 */
public class ServerException extends RuntimeException {
    public ServerException() {
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message) {
        super(message);
    }
}
