package com.teamk.scoretrack.module.commons.form.rest;

import com.teamk.scoretrack.module.commons.form.BaseForm;
import org.springframework.security.core.Authentication;

public class RestForm<DTO> extends BaseForm {
    private DTO dto;

    public RestForm(DTO dto) {
        this.dto = dto;
    }

    public RestForm(DTO dto, String bundleName) {
        this.dto = dto;
        this.bundleName = bundleName;
    }

    public RestForm(DTO dto, Authentication authentication) {
        this.dto = dto;
        this.authentication = authentication;
    }

    public RestForm(DTO dto, String bundleName, Authentication authentication) {
        this.dto = dto;
        this.bundleName = bundleName;
        this.authentication = authentication;
    }

    public DTO getDto() {
        return dto;
    }

    public void setDto(DTO dto) {
        this.dto = dto;
    }
}
