package com.teamk.scoretrack.module.core.entities.user.client.service.form;

import com.teamk.scoretrack.module.commons.form.rest.AbstractRestFormOptionsService;
import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.core.api.commons.base.dto.BundleResponse;
import com.teamk.scoretrack.module.core.entities.user.client.service.i18n.ProfilePageTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileInitFormOptionsService extends AbstractRestFormOptionsService<BundleResponse, RestForm<BundleResponse>> {
    private final ProfilePageTranslatorService profilePageTranslatorService;

    @Autowired
    public ProfileInitFormOptionsService(ProfilePageTranslatorService profilePageTranslatorService) {
        this.profilePageTranslatorService = profilePageTranslatorService;
    }

    @Override
    public void prepareFormOptions(RestForm<BundleResponse> form) {
        form.getDto().setBundle(profilePageTranslatorService.getMessages());
    }
}
