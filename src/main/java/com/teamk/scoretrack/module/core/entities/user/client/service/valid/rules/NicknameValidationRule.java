package com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;

import java.util.Optional;
import java.util.regex.Pattern;

public class NicknameValidationRule implements ValidationRule<ProfileUpdateDto> {
    private static final Pattern NICKNAME = Pattern.compile("^(?=.{5,15}$)[A-Za-z\\d]+\\w[A-Za-z\\d]+");

    @Override
    public Optional<ValidationRuleViolation> apply(ProfileUpdateDto dto) {
        if (ValidationRule.isNotValidRegex(NICKNAME, dto.getNickname())) {
            return Optional.of(new ValidationRuleViolation("nickname", "error.nickname"));
        }
        return Optional.empty();
    }
}