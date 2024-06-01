package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.scheduler;

import com.teamk.scoretrack.module.commons.base.service.BaseTransactionManager;
import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.exception.BaseErrorMapException;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.scheduler.APINbaSchedulerService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SeasonUpdateStrategy;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamSeasonUpdateStrategy;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamsResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamsStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.TeamDataEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert.TeamDtoEntityConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert.TeamStatsDtoEntityConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TeamSchedulerService extends APINbaSchedulerService {
    private final TeamDtoEntityConvertService convertService;
    private final TeamDataEntityService teamDataService;
    private final BaseTransactionManager transactionManager;
    private final TeamStatsDtoEntityConvertService statsConvertService;
    private final Logger LOGGER;
    private static final String TEAMS_ENDPOINT = "/teams";
    private static final String TEAM_STATS_ENDPOINT = TEAMS_ENDPOINT.concat("/statistics");

    public TeamSchedulerService(@Qualifier("nbaapiTeamMappingContext") MappingContext<TeamData, APINbaTeamResponseDto> mappingContext,
                                DtoEntityValidator<APINbaTeamResponseDto, FormValidationContext<APINbaTeamResponseDto>> validator, TeamDataEntityService teamDataService, BaseTransactionManager transactionManager, TeamStatsDtoEntityConvertService statsConvertService) {
        this.teamDataService = teamDataService;
        this.transactionManager = transactionManager;
        this.statsConvertService = statsConvertService;
        this.convertService = new TeamDtoEntityConvertService(mappingContext, validator);
        LOGGER = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public APINbaUpdate.Status startUpdate(List<SupportedSeasons> seasonsToUpdate, AtomicReference<APINbaUpdate.Status> updateStatus) {
        LOGGER.warn("Updating teams has started.");
        APINbaTeamsResponseDto apiNbaTeamsResponseDto = externalService.callApiSafe(TEAMS_ENDPOINT, APINbaTeamsResponseDto.class, new APINbaTeamsResponseDto(), LOGGER);
        List<TeamData> toSave = new ArrayList<>();
        for (APINbaTeamResponseDto dto : apiNbaTeamsResponseDto.getResponse()) {
            transactionManager.doInNewTransaction(() -> {
                try {
                    Optional<TeamData> wrapper = convertService.toEntity(dto);
                    if (wrapper.isPresent()) {
                        TeamData teamData = wrapper.get();
                        // Somehow the api thinks that team 'Home Stephen A' is an nba franchise team
                        if (teamData.isNbaFranchise() && !teamData.getExternalId().equals("37")) {
                            seasonsToUpdate.forEach(s -> setTeamStats(s, teamData));
                        }
                        // String id = teamDataService.update(teamData.getExternalId(), teamData);
                        LOGGER.info(String.format("Entity handled: %s", teamData.getExternalId()));
                        toSave.add(teamData);
                    }
                } catch (BaseErrorMapException e) {
                    updateStatus.set(APINbaUpdate.Status.WITH_ERRORS);
                    LOGGER.error(String.format("TD form: %s", dto));
                    LOGGER.error(String.format("Errors: %s", e.getErrorMap().getErrors().toString()));
                } catch (Exception e) {
                    updateStatus.set(APINbaUpdate.Status.WITH_ERRORS);
                    LOGGER.error(e.getMessage());
                }
            });
        }
        APINbaUpdate.Status currentStatus = updateStatus.get();
        if (!currentStatus.isWithErrors()) {
            teamDataService.updateAll(toSave);
            LOGGER.info("Entities updated successfully.");
        }
        LOGGER.warn("Updating teams has finished.");
        return currentStatus;
    }

    private void setTeamStats(SupportedSeasons season, TeamData teamData) {
        APINbaTeamsStatsDto teamStatsDto = externalService.callApiSafe(TEAM_STATS_ENDPOINT.concat("?id=%s&season=%s".formatted(teamData.getExternalId(), season.getYear())), APINbaTeamsStatsDto.class, new APINbaTeamsStatsDto(), LOGGER);
        List<APINbaTeamStatsDto> response = teamStatsDto.getResponse();
        if (!response.isEmpty()) {
            statsConvertService.toEntity(response.stream().findFirst().orElseThrow())
                    .ifPresent(teamStats -> teamData.getStatsBySeason().put(season, teamStats));
        }
    }

    @Override
    protected String getCollectionName() {
        return TeamData.COLLECTION_NAME;
    }

    @Override
    protected SeasonUpdateStrategy getSeasonUpdateStrategy() {
        return new TeamSeasonUpdateStrategy();
    }
}
