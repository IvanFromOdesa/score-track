package com.teamk.scoretrack.module.security.auth.service.valid;

import com.teamk.scoretrack.module.commons.base.service.valid.email.EmailValidationContext;
import com.teamk.scoretrack.module.commons.base.service.valid.email.StandardServerProvidedEmailValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.security.auth.config.AuthenticationConfiguration;
import com.teamk.scoretrack.module.security.auth.dto.SignUpForm;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthenticationSignUpFormValidator implements DtoEntityValidator<SignUpForm, FormValidationContext<SignUpForm>> {
    private static final Pattern NUMBERS_AND_LETTERS = Pattern.compile("(?=.{5,15}$)[A-Za-z\\d]+[\\w][A-Za-z\\d]+"); // with underscore
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}$");
    private final AuthTranslatorService translatorService;
    private final AuthenticationExistsValidator authenticationExistsValidator;
    private final StandardServerProvidedEmailValidator emailValidator;
    private final List<String> bannedWords;

    @Autowired
    public AuthenticationSignUpFormValidator(AuthTranslatorService translatorService, AuthenticationExistsValidator authenticationExistsValidator, StandardServerProvidedEmailValidator emailValidator, @Qualifier(AuthenticationConfiguration.BANNED_WORDS) List<String> bannedWords) {
        this.translatorService = translatorService;
        this.authenticationExistsValidator = authenticationExistsValidator;
        this.emailValidator = emailValidator;
        this.bannedWords = bannedWords;
    }

    @Override
    public ErrorMap validate(FormValidationContext<SignUpForm> context) {
        SignUpForm signUpForm = context.getDto();
        ErrorMap errors = context.getErrorMap();
        if (!signUpForm.isTosChecked()) {
            putErrorMsg(errors, "error.tos", "errors.tos");
        }
        String loginname = signUpForm.getLoginname();
        Optional<String> banned = bannedWords.stream().filter(word -> loginname.toLowerCase().contains(word)).findFirst();
        if (banned.isPresent()) {
            putErrorMsg(errors, "error.loginname", "errors.loginname.available");
        } else if (!NUMBERS_AND_LETTERS.matcher(loginname).matches()) {
            putErrorMsg(errors, "error.loginname", "errors.loginname");
        }
        String email = signUpForm.getEmail();
        errors.putAll(emailValidator.validate(new EmailValidationContext(email, "error.email", translatorService.getMessage("errors.email"))).getErrors());
        errors.putAll(authenticationExistsValidator.validate(loginname, email).getErrors());
        String password = signUpForm.getPassword();
        if (!PASSWORD.matcher(password).matches()) {
            putErrorMsg(errors, "error.password", "errors.password");
        }
        if (!password.equals(signUpForm.getConfirmPassword())) {
            putErrorMsg(errors, "error.password.confirm", "errors.confirm.password");
        }
        return errors;
    }

    private void putErrorMsg(ErrorMap errors, String cause, String code) {
        errors.put(cause, translatorService.getMessage(code));
    }
}
