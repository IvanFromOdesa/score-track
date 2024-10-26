package com.teamk.scoretrack.module.security.pwdreset.service.valid;

import com.teamk.scoretrack.module.commons.base.service.valid.email.EmailValidationContext;
import com.teamk.scoretrack.module.commons.base.service.valid.email.StandardServerProvidedEmailValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.security.auth.dto.PasswordForm;
import com.teamk.scoretrack.module.security.auth.service.valid.rules.AuthenticationPasswordValidationRule;
import com.teamk.scoretrack.module.security.pwdreset.dto.PwdResetForm;
import com.teamk.scoretrack.module.security.pwdreset.service.i18n.PwdResetTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PwdResetValidator implements DtoEntityValidator<PasswordForm, FormValidationContext<PasswordForm>> {
    private final AuthenticationPasswordValidationRule passwordValidationRule;
    private final PwdResetTranslatorService translatorService;
    private final StandardServerProvidedEmailValidator emailValidator;

    @Autowired
    public PwdResetValidator(AuthenticationPasswordValidationRule passwordValidationRule,
                             PwdResetTranslatorService translatorService,
                             StandardServerProvidedEmailValidator emailValidator) {
        this.passwordValidationRule = passwordValidationRule;
        this.translatorService = translatorService;
        this.emailValidator = emailValidator;
    }

    @Override
    public ErrorMap validate(FormValidationContext<PasswordForm> context) {
        ErrorMap errorMap = context.getErrorMap();
        passwordValidationRule.apply(context.getDto()).ifPresent(v -> putErrorMsg(errorMap, v.cause(), v.code()));
        return errorMap;
    }

    public ErrorMap validateEmail(FormValidationContext<PwdResetForm> context) {
        ErrorMap errorMap = context.getErrorMap();
        errorMap.putAll(emailValidator.validate(
                new EmailValidationContext(
                        context.getDto().getEmail(),
                        "error.email",
                        translatorService.getMessage("errors.email")))
                .getErrors());
        return errorMap;
    }

    private void putErrorMsg(ErrorMap errors, String cause, String code) {
        errors.put(cause, translatorService.getMessage(code));
    }
}
