package com.teamk.scoretrack.module.core.entities.user.client.service.valid;

import com.teamk.scoretrack.module.commons.base.service.valid.ValidationRule;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.core.entities.SportType;
import com.teamk.scoretrack.module.core.entities.io.FileType;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;
import com.teamk.scoretrack.module.core.entities.user.client.service.i18n.ProfilePageTranslatorService;
import com.teamk.scoretrack.module.core.entities.user.client.service.valid.rules.*;
import com.teamk.scoretrack.module.security.io.service.valid.FileValidationContext;
import com.teamk.scoretrack.module.security.io.service.valid.IFileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileUpdateValidator implements DtoEntityValidator<ProfileUpdateDto, ProfileUpdateFormValidationContext> {
    private static final List<ValidationRule<ProfileUpdateDto>> VALIDATION_RULES = List.of
            (
                    new DobValidationRule(),
                    new FirstNameValidationRule(),
                    new LastNameValidationRule(),
                    new InstagramProfileValidationRule(),
                    new XProfileValidationRule(),
                    new NicknameValidationRule()
            );
    private final IFileValidator<FileValidationContext> fileValidator;
    private final ProfilePageTranslatorService translatorService;

    @Autowired
    public ProfileUpdateValidator(IFileValidator<FileValidationContext> fileValidator, ProfilePageTranslatorService translatorService) {
        this.fileValidator = fileValidator;
        this.translatorService = translatorService;
    }

    @Override
    public ErrorMap validate(ProfileUpdateFormValidationContext ctx) {
        ProfileUpdateDto dto = ctx.getDto();
        ErrorMap errors = ctx.getErrorMap();
        MultipartFile profileImg = ctx.getProfileImg();
        if (profileImg != null) {
            errors.putAll(fileValidator.validate(new FileValidationContext(profileImg, List.of(FileType.IMG), "profileImg")).getErrors());
        }
        if (dto != null) {
            VALIDATION_RULES.stream().map(r -> r.apply(dto)).flatMap(Optional::stream).forEach(v -> putErrorMsg(errors, v.cause(), v.code()));
            List<SportType> sportTypes = dto.getSportPreference();
            if (sportTypes != null && sportTypes.contains(SportType.UNDEFINED)) {
                putErrorMsg(errors, "sportPreference", "error.sportPreference");
            }
        }
        return errors;
    }

    private void putErrorMsg(ErrorMap errors, String cause, String code) {
        errors.put(cause, translatorService.getMessage(code));
    }
}
