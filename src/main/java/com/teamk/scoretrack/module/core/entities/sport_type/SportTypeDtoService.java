package com.teamk.scoretrack.module.core.entities.sport_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SportTypeDtoService {
    private final SportTypeTranslatorService translatorService;

    @Autowired
    public SportTypeDtoService(SportTypeTranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    public SportTypeDto toDto(SportType sportType) {
        return new SportTypeDto(sportType, translatorService.getMessage(sportType.getBundleKey()));
    }
}
