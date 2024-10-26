package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service;

import com.teamk.scoretrack.module.core.api.commons.base.UiHint;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.i18n.APINbaPlayerStatCategoriesTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class APINbaPlayerStatCategoriesUiHintService {
    private final APINbaPlayerStatCategoriesTranslatorService infoHelperTranslatorService;

    @Autowired
    public APINbaPlayerStatCategoriesUiHintService(APINbaPlayerStatCategoriesTranslatorService infoHelperTranslatorService) {
        this.infoHelperTranslatorService = infoHelperTranslatorService;
    }

    public UiHint getUiHint(String titleCode, String descriptionCode, String className) {
        Map<String, String> messages = infoHelperTranslatorService.getMessages();
        return new UiHint(messages.get(titleCode), messages.get(descriptionCode), className);
    }
}
