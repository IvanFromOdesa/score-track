package com.teamk.scoretrack.module.core.entities.user.client.service.valid;

import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileUpdateDto;
import org.springframework.web.multipart.MultipartFile;

public class ProfileUpdateFormValidationContext extends FormValidationContext<ProfileUpdateDto> {
    private final MultipartFile profileImg;

    public ProfileUpdateFormValidationContext(ProfileUpdateDto dto, MultipartFile profileImg) {
        super(dto);
        this.profileImg = profileImg;
    }

    public MultipartFile getProfileImg() {
        return profileImg;
    }
}
