package com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRuleViolation;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;

import java.util.Optional;
import java.util.regex.Pattern;

public class InstagramProfileValidationRule implements ValidationRule<ProfileUpdateDto> {
    public static final Pattern INSTAGRAM_PROFILE = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?instagram\\.com\\/([a-zA-Z\\d\\.\\_\\-]+)?");

    @Override
    public Optional<ValidationRuleViolation> apply(ProfileUpdateDto dto) {
        if (ValidationRule.isNotValidRegex(INSTAGRAM_PROFILE, dto.getInstagramLink())) {
            return Optional.of(new ValidationRuleViolation("instagramLink", "error.instagram.link"));
        }
        return Optional.empty();
    }
}