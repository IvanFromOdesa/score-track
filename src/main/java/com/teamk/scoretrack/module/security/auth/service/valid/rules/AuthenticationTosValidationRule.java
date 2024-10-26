package com.teamk.scoretrack.module.security.auth.service.valid.rules;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.security.auth.dto.SignUpForm;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationTosValidationRule implements ValidationRule<SignUpForm> {
    @Override
    public Optional<ValidationRuleViolation> apply(SignUpForm signUpForm) {
        return signUpForm.isTosChecked() ? Optional.empty() : Optional.of(new ValidationRuleViolation("error.tos", "errors.tos"));
    }
}
