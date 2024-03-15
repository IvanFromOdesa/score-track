package com.teamk.scoretrack.module.commons.base.service.valid;

import java.util.Optional;
import java.util.regex.Pattern;

@FunctionalInterface
public interface ValidationRule<T> {
    Pattern NAME = Pattern.compile("^[^\\s\\n\\d_!¡?÷¿\\/\\\\+=@#$%ˆ&*(){}|~<>;:\\]\\[]{2,}$");

    Optional<ValidationRuleViolation> apply(T t);

    static boolean isNotValidRegex(Pattern pattern, String input) {
        return input != null && !input.isBlank() && !pattern.matcher(input).matches();
    }
}
