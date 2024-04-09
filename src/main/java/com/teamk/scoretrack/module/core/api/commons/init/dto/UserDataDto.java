package com.teamk.scoretrack.module.core.api.commons.init.dto;

import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;

public abstract class UserDataDto {
    private AccessToken token;
    private String recaptchaKey;
    private String locale;

    public AccessToken getToken() {
        return token;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }

    public String getRecaptchaKey() {
        return recaptchaKey;
    }

    public void setRecaptchaKey(String recaptchaKey) {
        this.recaptchaKey = recaptchaKey;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
