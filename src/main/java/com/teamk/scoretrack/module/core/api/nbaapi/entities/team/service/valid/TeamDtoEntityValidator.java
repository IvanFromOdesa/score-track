package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.valid;

import com.teamk.scoretrack.module.commons.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.commons.service.valid.image.IImgValidator;
import com.teamk.scoretrack.module.commons.service.valid.image.ImageValidationContext;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import org.springframework.stereotype.Service;

@Service
public class TeamDtoEntityValidator implements DtoEntityValidator<APINbaTeamResponseDto, FormValidationContext<APINbaTeamResponseDto>> {
    private final IImgValidator<ImageValidationContext> imgValidator;

    public TeamDtoEntityValidator(IImgValidator<ImageValidationContext> imgValidator) {
        this.imgValidator = imgValidator;
    }

    @Override
    public ErrorMap validate(FormValidationContext<APINbaTeamResponseDto> context) {
        String logo = context.getDto().getLogo();
        ErrorMap errorMap = context.getErrorMap();
        if (logo != null) {
            errorMap.putAll(imgValidator.validate(new ImageValidationContext(logo, "logo")).getErrors());
        } /*else {
            errorMap.put("logo", "No logo provided.");
        }*/
        return errorMap;
    }
}
