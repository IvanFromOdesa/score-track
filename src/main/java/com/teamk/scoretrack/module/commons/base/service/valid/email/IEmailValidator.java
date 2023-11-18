package com.teamk.scoretrack.module.commons.base.service.valid.email;

import com.teamk.scoretrack.module.commons.other.ErrorMap;

public interface IEmailValidator<CONTEXT extends EmailValidationContext> {
    ErrorMap validate(CONTEXT context);
}
