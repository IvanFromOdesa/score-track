package com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;

import java.util.Optional;

public class FirstNameValidationRule implements ValidationRule<ProfileUpdateDto> {
    @Override
    public Optional<ValidationRuleViolation> apply(ProfileUpdateDto dto) {
        if (ValidationRule.isNotValidRegex(NAME, dto.getFirstName())) {
            return Optional.of(new ValidationRuleViolation("firstName", "error.name"));
        }
        return Optional.empty();
    }
}