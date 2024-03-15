package com.teamk.scoretrack.module.security.ipblocker.exception;

import com.teamk.scoretrack.module.security.commons.ForbiddenResponseException;

public class IpBlockException extends ForbiddenResponseException {
    public IpBlockException(String msg) {
        super(msg);
    }
}
