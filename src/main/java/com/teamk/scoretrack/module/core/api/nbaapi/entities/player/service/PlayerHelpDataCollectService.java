package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.APINbaUpdateEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.IAPINbaHelpDataCollectService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.i18n.APINbaInfoHelperTranslatorService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerStatCategoryDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayersHelpData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.util.SupportedSeasonsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PlayerHelpDataCollectService implements IAPINbaHelpDataCollectService {
    private final APINbaUpdateEntityService updateEntityService;
    private final APINbaInfoHelperTranslatorService infoHelperTranslatorService;

    @Autowired
    public PlayerHelpDataCollectService(APINbaUpdateEntityService updateEntityService,
                                        APINbaInfoHelperTranslatorService infoHelperTranslatorService) {
        this.updateEntityService = updateEntityService;
        this.infoHelperTranslatorService = infoHelperTranslatorService;
    }

    @Override
    public APINbaPlayersHelpData getHelpData() {
        List<SupportedSeasons> seasons = updateEntityService.getAvailableSeasonsForCollection(PlayerData.COLLECTION_NAME);
        return new APINbaPlayersHelpData(SupportedSeasonsUtils.transformToArrayDto(seasons), getStatCategories());
    }

    private APINbaPlayerStatCategoryDto[] getStatCategories() {
        return Arrays.stream(PlayerDataStatCategoryInfoHelper.values())
                .map(p -> new APINbaPlayerStatCategoryDto(p.getStatName(), infoHelperTranslatorService.getMessage(p.getUiHintDropdownTitleCode(), "players")))
                .toArray(APINbaPlayerStatCategoryDto[]::new);
    }

    @Override
    public String getComponentName() {
        return PlayerData.COLLECTION_NAME;
    }
}
