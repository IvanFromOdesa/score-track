package com.teamk.scoretrack.module.commons.base.service.valid.form;

import com.teamk.scoretrack.module.commons.base.service.valid.CommonValidationContext;

public class FormValidationContext<DTO> extends CommonValidationContext {
    protected final DTO dto;

    public FormValidationContext(DTO dto) {
        super();
        this.dto = dto;
    }

    public DTO getDto() {
        return dto;
    }
}
