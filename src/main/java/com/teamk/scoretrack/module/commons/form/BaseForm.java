package com.teamk.scoretrack.module.commons.form;

import org.springframework.security.core.Authentication;

public abstract class BaseForm {
    protected String bundleName;
    protected Authentication authentication;

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
