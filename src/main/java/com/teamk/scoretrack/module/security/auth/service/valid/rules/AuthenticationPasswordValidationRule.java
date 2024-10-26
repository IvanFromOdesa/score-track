package com.teamk.scoretrack.module.security.auth.service.valid.rules;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.security.auth.dto.PasswordForm;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

// TODO: should be validator
@Component
public class AuthenticationPasswordValidationRule implements ValidationRule<PasswordForm> {
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}$");

    @Override
    public Optional<ValidationRuleViolation> apply(PasswordForm pwdForm) {
        String password = pwdForm.getPassword();
        if (!PASSWORD.matcher(password).matches()) {
            return Optional.of(new ValidationRuleViolation("error.password", "errors.password"));
        }
        if (!password.equals(pwdForm.getConfirmPassword())) {
            return Optional.of(new ValidationRuleViolation("error.password.confirm", "errors.confirm.password"));
        }
        return Optional.empty();
    }
}
