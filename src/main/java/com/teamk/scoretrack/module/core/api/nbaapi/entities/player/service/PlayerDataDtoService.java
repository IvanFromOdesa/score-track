package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service;

import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoDtoService;
import com.teamk.scoretrack.module.core.api.commons.base.UiHint;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerAvgStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.*;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert.PlayerAvgStatsDtoEntityConverter;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert.PlayerDataLeaderboardEntityDtoConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.util.SupportedSeasonsUtils;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PreAuthorize("hasApiAccess(T(com.teamk.scoretrack.module.core.entities.sport_api.SportAPI).API_NBA.key) && @APINbaUpdateEntityService.isAccessible('players')")
public class PlayerDataDtoService extends AbstractMongoDtoService<PlayerData, APINbaPlayerResponseDto, PlayerDataEntityService> {
    private final PlayerDataLeaderboardEntityDtoConvertService efficiencyEntityDtoConvertService;
    private final PlayerAvgStatsDtoEntityConverter avgStatsDtoEntityConverter;
    private final PlayerDataStatCategoryDtoService statCategoryDtoService;
    private final APINbaPlayerStatCategoriesUiHintService statCategoriesUiHintService;

    @Autowired
    public PlayerDataDtoService(PlayerDataLeaderboardEntityDtoConvertService efficiencyEntityDtoConvertService,
                                PlayerAvgStatsDtoEntityConverter avgStatsDtoEntityConverter,
                                PlayerDataStatCategoryDtoService statCategoryDtoService,
                                APINbaPlayerStatCategoriesUiHintService statCategoriesUiHintService) {
        this.efficiencyEntityDtoConvertService = efficiencyEntityDtoConvertService;
        this.avgStatsDtoEntityConverter = avgStatsDtoEntityConverter;
        this.statCategoryDtoService = statCategoryDtoService;
        this.statCategoriesUiHintService = statCategoriesUiHintService;
    }

    public Optional<APINbaPlayerDetailedDto> getById(String id) {
        return entityService.getById(id).map(p -> {
            Map<SupportedSeasons, TeamData> teamBySeason = p.getTeamBySeason();
            return new APINbaPlayerDetailedDto(convertService.toDto(p), SupportedSeasonsUtils.transformToArrayDto(teamBySeason.keySet()));
        });
    }

    public APINbaPlayersLeaderboardDto getLeaderboardPlayers(PlayerDataStatCategoryInfoHelper type, int season, int playerCount) {
        List<APINbaPlayerLeaderboardDto> data = getLeaderboardDtoData(entityService.getLeaderboardPlayers(type, season, playerCount));
        return new APINbaPlayersLeaderboardDto(data, getUiHint(type));
    }

    private UiHint getUiHint(PlayerDataStatCategoryInfoHelper type) {
        return statCategoriesUiHintService.getUiHint(type.getUiHintTitleCode(), type.getUiHintDescriptionCode(), type.getUiHintClassName());
    }

    private List<APINbaPlayerLeaderboardDto> getLeaderboardDtoData(List<PlayerDataLeaderboardProjection> topEfficiencyPlayers) {
        return topEfficiencyPlayers.stream().map(efficiencyEntityDtoConvertService::toDto).toList();
    }
    
    public APINbaPlayerAvgStatsMapDto getAvgPlayerStatsPerSeason(String externalId) {
        Map<SupportedSeasons, PlayerAvgStats> avgPlayerStatsPerSeason = entityService.getAvgPlayerStatsPerSeason(externalId);
        Map<String, APINbaPlayerAvgStatsDto> data = avgPlayerStatsPerSeason.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> String.valueOf(e.getKey().getYear()),
                        e -> avgStatsDtoEntityConverter.toDto(e.getValue()))
                );
        return new APINbaPlayerAvgStatsMapDto(
                SupportedSeasonsUtils.transformToArrayDto(avgPlayerStatsPerSeason.keySet()),
                data,
                statCategoryDtoService.getGroupedStatBoardCategoriesDtoMap()
        );
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
