package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service;

import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.core.api.commons.base.UiHint;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.config.APINbaResourceConfiguration;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.APINbaDtoService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.APINbaUiHintService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerHeadshotImg;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerLeaderboardDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayersLeaderboardDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert.PlayerEfficiencyEntityDtoConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@PreAuthorize("hasApiAccess(T(com.teamk.scoretrack.module.core.entities.SportAPI).API_NBA.key) && @APINbaUpdateEntityService.isAccessible('players')")
public class PlayerDataDtoService extends APINbaDtoService<PlayerData, APINbaPlayerResponseDto, PlayerDataEntityService> {
    private final PlayerEfficiencyEntityDtoConvertService efficiencyEntityDtoConvertService;
    private final Map<String, PlayerHeadshotImg> playerProfileImgs;

    @Autowired
    public PlayerDataDtoService(PlayerEfficiencyEntityDtoConvertService efficiencyEntityDtoConvertService,
                                @Qualifier(APINbaResourceConfiguration.PLAYERS_IMGS) Map<String, PlayerHeadshotImg> playerProfileImgs) {
        this.efficiencyEntityDtoConvertService = efficiencyEntityDtoConvertService;
        this.playerProfileImgs = playerProfileImgs;
    }

    public APINbaPlayersLeaderboardDto getLeaderboardPlayers(PlayerDataStatCategoryInfoHelper type, int season, int playerCount) {
        List<APINbaPlayerLeaderboardDto> data = getLeaderboardDtoData(entityService.getLeaderboardPlayers(type, season, playerCount));
        return new APINbaPlayersLeaderboardDto(data, getUiHint(type));
    }

    private UiHint getUiHint(PlayerDataStatCategoryInfoHelper type) {
        return uiHintService.getUiHint(APINbaUiHintService.BundleName.PLAYERS, type.getUiHintTitleCode(), type.getUiHintDescriptionCode(), type.getUiHintClassName());
    }

    private List<APINbaPlayerLeaderboardDto> getLeaderboardDtoData(List<PlayerDataLeaderboardProjection> topEfficiencyPlayers) {
        return topEfficiencyPlayers.stream()
                .map(efficiencyEntityDtoConvertService::toDto).peek(p -> {
                    PlayerHeadshotImg playerHeadshotImg = playerProfileImgs.get(p.getId());
                    if (playerHeadshotImg != null) {
                        p.setImgUrl(playerHeadshotImg.imgUrl());
                    }
                }).toList();
    }

    @Override
    @Autowired
    protected void setEntityService(PlayerDataEntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    @Autowired
    protected void setConvertService(DtoEntityConvertService<PlayerData, APINbaPlayerResponseDto> convertService) {
        this.convertService = convertService;
    }
}
