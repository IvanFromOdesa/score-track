package com.teamk.scoretrack.module.core.entities.user.client.service.form;

import com.teamk.scoretrack.module.commons.form.rest.AbstractRestFormOptionsService;
import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.core.entities.user.client.dto.ProfileInitResponse;
import com.teamk.scoretrack.module.core.entities.user.client.service.i18n.ProfilePageTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProfileInitFormOptionsService extends AbstractRestFormOptionsService<ProfileInitResponse, RestForm<ProfileInitResponse>> {
    private final ProfilePageTranslatorService profilePageTranslatorService;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileUploadSize;

    @Autowired
    public ProfileInitFormOptionsService(ProfilePageTranslatorService profilePageTranslatorService) {
        this.profilePageTranslatorService = profilePageTranslatorService;
    }

    @Override
    public void prepareFormOptions(RestForm<ProfileInitResponse> form) {
        ProfileInitResponse dto = form.getDto();
        dto.setBundle(profilePageTranslatorService.getMessages());
        dto.setMaxUploadFileSize(maxFileUploadSize);
    }
}
