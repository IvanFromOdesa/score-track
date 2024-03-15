package com.teamk.scoretrack.module.security.recaptcha.exception;

import com.teamk.scoretrack.module.security.commons.ForbiddenResponseException;

public class RecaptchaInvalidResponse extends ForbiddenResponseException {
    public RecaptchaInvalidResponse(String msg) {
        super(msg);
    }
}
