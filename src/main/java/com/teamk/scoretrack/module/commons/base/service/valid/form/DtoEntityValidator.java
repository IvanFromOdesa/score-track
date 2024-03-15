package com.teamk.scoretrack.module.commons.base.service.valid.form;

import com.teamk.scoretrack.module.commons.other.ErrorMap;

@FunctionalInterface
public interface DtoEntityValidator<DTO, CONTEXT extends FormValidationContext<DTO>> {
    ErrorMap validate(CONTEXT context);

    static <D, C extends FormValidationContext<D>> DtoEntityValidator<D, C> withDefaults() {
        return context -> ErrorMap.empty();
    }
}
