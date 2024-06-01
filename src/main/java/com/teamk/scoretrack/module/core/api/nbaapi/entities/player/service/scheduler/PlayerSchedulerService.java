package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.scheduler;

import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.scheduler.APINbaSchedulerService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerSeasonUpdateStrategy;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayersResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayersStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.PlayerDataEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert.PlayerDtoEntityConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert.PlayerStatsDtoEntityConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SeasonUpdateStrategy;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.TeamDataEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PlayerSchedulerService extends APINbaSchedulerService {
    private final TeamDataEntityService teamDataEntityService;
    private final PlayerDataEntityService playerDataEntityService;
    private final PlayerDtoEntityConvertService entityConvertService;
    private final PlayerStatsDtoEntityConvertService statsDtoEntityConvertService;
    private final Logger LOGGER;
    private static final String PLAYERS_ENDPOINT = "/players";
    private static final String PLAYER_STATS_ENDPOINT = PLAYERS_ENDPOINT.concat("/statistics");

    @Autowired
    public PlayerSchedulerService(TeamDataEntityService teamDataEntityService,
                                  PlayerDataEntityService playerDataEntityService,
                                  PlayerDtoEntityConvertService entityConvertService,
                                  PlayerStatsDtoEntityConvertService statsDtoEntityConvertService) {
        this.teamDataEntityService = teamDataEntityService;
        this.playerDataEntityService = playerDataEntityService;
        this.entityConvertService = entityConvertService;
        this.statsDtoEntityConvertService = statsDtoEntityConvertService;
        LOGGER = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    protected APINbaUpdate.Status startUpdate(List<SupportedSeasons> seasonsToUpdate, AtomicReference<APINbaUpdate.Status> updateStatus) {
        LOGGER.warn("Updating players has started.");
        // 30 nba teams. Update player data by 5 teams at once.
        // Save the processed data.
        for (int i = 0; i < 6; i ++) {
            List<TeamData> teamData = getTeamData(i);
            teamData.forEach(td -> updatePlayerData(updateStatus, seasonsToUpdate, td));
        }
        LOGGER.warn("Updating players has finished.");
        return updateStatus.get();
    }

    private void updatePlayerData(AtomicReference<APINbaUpdate.Status> updateStatus, List<SupportedSeasons> seasonsToUpdate, TeamData td) {
        final Executor executor = Executors.newFixedThreadPool(2);
        for (SupportedSeasons season : seasonsToUpdate) {
            final CompletableFuture<List<PlayerStats>> statsFuture = CompletableFuture.supplyAsync(() -> getPlayerStats(td, season), executor);
            CompletableFuture.supplyAsync(() -> getPlayerData(td, season), executor).thenCombine(statsFuture, (playerData, playerStats) -> {
                playerData.forEach(pd -> pd.getStatsBySeason().put(season, playerStats.stream().filter(ps -> Objects.equals(ps.getPlayer().id(), pd.getExternalId())).toList()));
                return playerData;
            }).exceptionally(e -> {
                LOGGER.error("Error while updating player data: %s. TD: %s, seasons: %s".formatted(e.getMessage(), td.getExternalId(), season.getYear()));
                updateStatus.set(APINbaUpdate.Status.WITH_ERRORS);
                return List.of();
            }).thenAccept(playerDataList -> {
                playerDataList.forEach(pd -> LOGGER.info("Player data successfully handled: %s".formatted(pd)));
                playerDataEntityService.updateAll(playerDataList);
            }).join();
        }
    }

    private List<PlayerStats> getPlayerStats(TeamData td, SupportedSeasons season) {
        String endpoint = PLAYER_STATS_ENDPOINT.concat("?team=%s&season=%s".formatted(td.getExternalId(), season.getYear()));
        APINbaPlayersStatsDto responseDto = externalService.callApiSafe(endpoint, APINbaPlayersStatsDto.class, new APINbaPlayersStatsDto(), LOGGER);
        List<PlayerStats> result = new ArrayList<>();
        responseDto.getResponse().forEach(dto -> statsDtoEntityConvertService.toEntity(dto).ifPresent(result::add));
        return result;
    }

    private List<PlayerData> getPlayerData(TeamData td, SupportedSeasons season) {
        String endpoint = PLAYERS_ENDPOINT.concat("?team=%s&season=%s".formatted(td.getExternalId(), season.getYear()));
        APINbaPlayersResponseDto responseDto = externalService.callApiSafe(endpoint, APINbaPlayersResponseDto.class, new APINbaPlayersResponseDto(), LOGGER);
        List<PlayerData> result = new ArrayList<>();
        for (APINbaPlayerResponseDto dto : responseDto.getResponse()) {
            Optional<PlayerData> wrapper = entityConvertService.toEntity(dto);
            if (wrapper.isPresent()) {
                PlayerData playerData = wrapper.get();
                playerData.getTeamBySeason().put(season, td);
                result.add(playerData);
            }
        }
        return result;
    }

    private List<TeamData> getTeamData(int page) {
        return teamDataEntityService.getAllAsList(page, 5);
    }

    @Override
    protected UpdateReadinessStatus getUpdateReadinessStatus() {
        int count = teamDataEntityService.getNbaFranchiseCount();
        ErrorMap errorMap = new ErrorMap();
        String cause = "teamData";
        if (count < 0) {
            errorMap.put(cause, "No team data has been found. This update requires present nba franchise teams.");
        } else if (count < 30) {
            errorMap.put(cause, "Update requires 30 nba franchise teams.");
        }
        return new UpdateReadinessStatus(errorMap.isEmpty(), errorMap);
    }

    @Override
    protected String getCollectionName() {
        return PlayerData.COLLECTION_NAME;
    }

    @Override
    protected SeasonUpdateStrategy getSeasonUpdateStrategy() {
        return new PlayerSeasonUpdateStrategy();
    }
}
