package com.teamk.scoretrack.module.commons.form.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public class MvcForm {
    private Model model;
    private String bundleName;
    private Authentication authentication;

    public MvcForm(Model model) {
        this.model = model;
    }

    public MvcForm(Model model, String bundleName) {
        this.model = model;
        this.bundleName = bundleName;
    }

    public MvcForm(Model model, Authentication authentication) {
        this.model = model;
        this.authentication = authentication;
    }

    public MvcForm(Model model, String bundleName, Authentication authentication) {
        this.model = model;
        this.bundleName = bundleName;
        this.authentication = authentication;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

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
