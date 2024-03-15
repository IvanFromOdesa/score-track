package com.teamk.scoretrack.module.security.io.service.valid;

import com.teamk.scoretrack.module.commons.other.ErrorMap;

public interface IFileValidator<CONTEXT extends FileValidationContext> {
    ErrorMap validate(CONTEXT context);
}
