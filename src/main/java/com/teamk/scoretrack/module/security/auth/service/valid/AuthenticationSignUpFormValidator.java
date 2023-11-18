package com.teamk.scoretrack.module.security.auth.service.valid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.commons.base.service.valid.email.EmailValidationContext;
import com.teamk.scoretrack.module.commons.base.service.valid.email.StandardServerProvidedEmailValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.security.auth.dao.AuthenticationDao;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.dto.SignUpForm;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthenticationSignUpFormValidator implements DtoEntityValidator<SignUpForm, FormValidationContext<SignUpForm>> {
    private static final Pattern NUMBERS_AND_LETTERS = Pattern.compile("(?=.{5,15}$)[A-Za-z\\d]+[\\w][A-Za-z\\d]+"); // with underscore
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}$");
    private final AuthTranslatorService translatorService;
    private final AuthenticationDao authenticationDao;
    private final StandardServerProvidedEmailValidator emailValidator;

    @Autowired
    public AuthenticationSignUpFormValidator(AuthTranslatorService translatorService, AuthenticationDao authenticationDao, StandardServerProvidedEmailValidator emailValidator) {
        this.translatorService = translatorService;
        this.authenticationDao = authenticationDao;
        this.emailValidator = emailValidator;
    }

    @Override
    public ErrorMap validate(FormValidationContext<SignUpForm> context) {
        SignUpForm signUpForm = context.getDto();
        ErrorMap errors = context.getErrorMap();
        if (!signUpForm.isTosChecked()) {
            putErrorMsg(errors, "error.tos", "errors.tos");
        }
        String loginname = signUpForm.getLoginname();
        Optional<String> banned = bannedWords().stream().filter(word -> loginname.toLowerCase().contains(word)).findFirst();
        if (banned.isPresent()) {
            putErrorMsg(errors, "error.loginname", "errors.loginname.available");
        } else if (!NUMBERS_AND_LETTERS.matcher(loginname).matches()) {
            putErrorMsg(errors, "error.loginname", "errors.loginname");
        } else {
            Optional<AuthenticationBean> byLoginname = authenticationDao.findByLoginname(loginname);
            byLoginname.ifPresent(authenticationBean -> errors.put("error.loginname", translatorService.getMessage("errors.loginname.unique", authenticationBean.getLoginname(), String.format("%s_%s", loginname, CommonsUtil.randomNChars(11)))));
        }
        String email = signUpForm.getEmail();
        Map<String, ErrorMap.Error> emailErrors = emailValidator.validate(new EmailValidationContext(email, "error.email", translatorService.getMessage("errors.email"))).getErrors();
        if (!emailErrors.isEmpty()) {
            errors.putAll(emailErrors);
        } else {
            authenticationDao.findByEmail(email).ifPresent(mail -> putErrorMsg(errors, "error.email", "errors.email.unique"));
        }
        String password = signUpForm.getPassword();
        if (!PASSWORD.matcher(password).matches()) {
            putErrorMsg(errors, "error.password", "errors.password");
        }
        return errors;
    }

    private void putErrorMsg(ErrorMap errors, String cause, String code) {
        errors.put(cause, translatorService.getMessage(code));
    }

    private List<String> bannedWords() {
        String path = "valid/banned.json";
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource != null) {
            File file = new File(resource.getFile());
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(file, new TypeReference<>() {});
            } catch (IOException e) {
                MessageLogger.error(e.getMessage(), e);
            }
        } else {
            MessageLogger.error("Can't get the resource file from FS: %s".formatted(path));
        }
        return new ArrayList<>();
    }
}
