package com.teamk.scoretrack.module.security.auth.service.valid;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.commons.base.service.valid.email.EmailValidationContext;
import com.teamk.scoretrack.module.commons.base.service.valid.email.StandardServerProvidedEmailValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.security.auth.dto.SignUpForm;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import com.teamk.scoretrack.module.security.auth.service.valid.rules.AuthenticationPasswordValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AuthenticationSignUpFormValidator implements DtoEntityValidator<SignUpForm, FormValidationContext<SignUpForm>> {
    private final AuthTranslatorService translatorService;
    private final AuthenticationExistsValidator authenticationExistsValidator;
    private final StandardServerProvidedEmailValidator emailValidator;
    private final List<ValidationRule<SignUpForm>> validationRules;
    private final AuthenticationPasswordValidationRule passwordValidationRule;

    @Autowired
    public AuthenticationSignUpFormValidator(AuthTranslatorService translatorService,
                                             AuthenticationExistsValidator authenticationExistsValidator,
                                             StandardServerProvidedEmailValidator emailValidator,
                                             List<ValidationRule<SignUpForm>> validationRules,
                                             AuthenticationPasswordValidationRule passwordValidationRule) {
        this.translatorService = translatorService;
        this.authenticationExistsValidator = authenticationExistsValidator;
        this.emailValidator = emailValidator;
        this.validationRules = validationRules;
        this.passwordValidationRule = passwordValidationRule;
    }

    @Override
    public ErrorMap validate(FormValidationContext<SignUpForm> context) {
        SignUpForm signUpForm = context.getDto();
        ErrorMap errors = context.getErrorMap();
        Consumer<ValidationRuleViolation> putErrorMsg = v -> putErrorMsg(errors, v.cause(), v.code());
        validationRules.stream().map(r -> r.apply(signUpForm)).flatMap(Optional::stream).forEach(putErrorMsg);
        passwordValidationRule.apply(signUpForm.getPwdForm()).ifPresent(putErrorMsg);
        String email = signUpForm.getEmail();
        errors.putAll(emailValidator.validate(new EmailValidationContext(email, "error.email", translatorService.getMessage("errors.email"))).getErrors());
        errors.putAll(authenticationExistsValidator.validate(signUpForm.getLoginname(), email).getErrors());
        return errors;
    }

    private void putErrorMsg(ErrorMap errors, String cause, String code) {
        errors.put(cause, translatorService.getMessage(code));
    }
}
