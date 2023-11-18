package com.teamk.scoretrack.module.commons.base.service.valid.form;

import com.teamk.scoretrack.module.commons.other.ErrorMap;

public interface DtoEntityValidator<DTO, CONTEXT extends FormValidationContext<DTO>> {
    ErrorMap validate(CONTEXT context);
}
