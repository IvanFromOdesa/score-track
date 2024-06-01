package com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.commons.base.service.valid.date.LocalDateValidator;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;

import java.time.LocalDate;
import java.util.Optional;

public class DobValidationRule implements ValidationRule<ProfileUpdateDto> {
    public static final LocalDateValidator DATE_VALIDATOR = new LocalDateValidator("yyyy-MM-dd", ld -> ld.isAfter(LocalDate.now()));

    @Override
    public Optional<ValidationRuleViolation> apply(ProfileUpdateDto dto) {
        String dob = dto.getDob();
        if (dob != null && !dob.isBlank() && !DATE_VALIDATOR.isValid(dob)) {
            return Optional.of(new ValidationRuleViolation("dob", "error.dob"));
        }
        return Optional.empty();
    }
}