package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.APINbaUpdateEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.IAPINbaHelpDataCollectService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayersHelpData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.util.SupportedSeasonsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerHelpDataCollectService implements IAPINbaHelpDataCollectService {
    private final APINbaUpdateEntityService updateEntityService;
    private final PlayerDataStatCategoryDtoService statCategoryDtoService;

    @Autowired
    public PlayerHelpDataCollectService(APINbaUpdateEntityService updateEntityService, PlayerDataStatCategoryDtoService statCategoryDtoService) {
        this.updateEntityService = updateEntityService;
        this.statCategoryDtoService = statCategoryDtoService;
    }

    @Override
    public APINbaPlayersHelpData getHelpData() {
        List<SupportedSeasons> seasons = updateEntityService.getAvailableSeasonsForCollection(PlayerData.COLLECTION_NAME);
        return new APINbaPlayersHelpData(SupportedSeasonsUtils.transformToArrayDto(seasons), statCategoryDtoService.getLeaderboardStatCategoryDtoArray());
    }

    @Override
    public String getComponentName() {
        return PlayerData.COLLECTION_NAME;
    }
}
