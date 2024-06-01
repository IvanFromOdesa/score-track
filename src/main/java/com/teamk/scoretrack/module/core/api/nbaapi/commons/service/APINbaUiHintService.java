package com.teamk.scoretrack.module.core.api.nbaapi.commons.service;

import com.teamk.scoretrack.module.core.api.commons.base.UiHint;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.i18n.APINbaInfoHelperTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class APINbaUiHintService {
    private final APINbaInfoHelperTranslatorService infoHelperTranslatorService;

    @Autowired
    public APINbaUiHintService(APINbaInfoHelperTranslatorService infoHelperTranslatorService) {
        this.infoHelperTranslatorService = infoHelperTranslatorService;
    }

    public UiHint getUiHint(BundleName bundleName, String titleCode) {
        return new UiHint(getMessage(bundleName.getName(), titleCode), null, null);
    }

    public UiHint getUiHint(String descriptionCode, BundleName bundleName) {
        return new UiHint(null, getMessage(bundleName.getName(), descriptionCode), null);
    }

    public UiHint getUiHint(BundleName bundleName, String titleCode, String descriptionCode) {
        Map<String, String> messages = infoHelperTranslatorService.getMessages(bundleName.getName());
        return new UiHint(messages.get(titleCode), messages.get(descriptionCode), null);
    }

    public UiHint getUiHint(BundleName bundleName, String titleCode, String descriptionCode, String className) {
        Map<String, String> messages = infoHelperTranslatorService.getMessages(bundleName.getName());
        return new UiHint(messages.get(titleCode), messages.get(descriptionCode), className);
    }

    private String getMessage(String bundleName, String bundleCode) {
        return infoHelperTranslatorService.getMessage(bundleCode, bundleName);
    }

    public enum BundleName {
        PLAYERS("players"), TEAMS("teams");

        private final String name;

        BundleName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
