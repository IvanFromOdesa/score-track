package com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;

import java.util.Optional;
import java.util.regex.Pattern;

public class XProfileValidationRule implements ValidationRule<ProfileUpdateDto> {
    public static final Pattern X_PROFILE = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?((x|twitter)\\.com)\\/([a-zA-Z\\d\\.\\_\\-]+)?");

    @Override
    public Optional<ValidationRuleViolation> apply(ProfileUpdateDto dto) {
        if (ValidationRule.isNotValidRegex(X_PROFILE, dto.getxLink())) {
            return Optional.of(new ValidationRuleViolation("xLink", "error.twitter.link"));
        }
        return Optional.empty();
    }
}