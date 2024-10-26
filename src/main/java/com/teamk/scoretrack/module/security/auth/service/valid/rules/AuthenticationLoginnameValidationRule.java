package com.teamk.scoretrack.module.security.auth.service.valid.rules;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.security.auth.config.AuthenticationConfiguration;
import com.teamk.scoretrack.module.security.auth.dto.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class AuthenticationLoginnameValidationRule implements ValidationRule<SignUpForm> {
    private static final Pattern NUMBERS_AND_LETTERS = Pattern.compile("(?=.{5,15}$)[A-Za-z\\d]+[\\w][A-Za-z\\d]+"); // with underscore
    private final List<String> bannedWords;

    @Autowired
    public AuthenticationLoginnameValidationRule(@Qualifier(AuthenticationConfiguration.BANNED_WORDS) List<String> bannedWords) {
        this.bannedWords = bannedWords;
    }

    @Override
    public Optional<ValidationRuleViolation> apply(SignUpForm signUpForm) {
        String loginname = signUpForm.getLoginname();
        Optional<String> banned = bannedWords.stream().filter(word -> loginname.toLowerCase().contains(word)).findFirst();
        if (banned.isPresent()) {
            return Optional.of(new ValidationRuleViolation("error.loginname", "errors.loginname.available"));
        } else if (!NUMBERS_AND_LETTERS.matcher(loginname).matches()) {
            return Optional.of(new ValidationRuleViolation("error.loginname", "errors.loginname"));
        }
        return Optional.empty();
    }
}
