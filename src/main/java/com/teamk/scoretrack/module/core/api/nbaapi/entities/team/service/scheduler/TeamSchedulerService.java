package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.scheduler;

import com.teamk.scoretrack.module.commons.base.service.BaseTransactionManager;
import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.exception.BaseErrorMapException;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.scheduler.APINbaSchedulerService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
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

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TeamSchedulerService extends APINbaSchedulerService {
    protected final TeamDtoEntityConvertService convertService;
    protected final TeamDataEntityService teamDataService;
    protected final BaseTransactionManager transactionManager;
    protected final TeamStatsDtoEntityConvertService statsConvertService;
    protected final Logger LOGGER;
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
    public void startUpdate() {
        LOGGER.warn("Updating teams has started.");
        AtomicReference<APINbaUpdate.Status> updateStatus = new AtomicReference<>(APINbaUpdate.Status.PROCESSING);
        Instant started = Instant.now();
        updateService.save(new APINbaUpdate(started, null, getCollectionName(), updateStatus.get()));
        APINbaTeamsResponseDto apiNbaTeamsResponseDto = externalService.callApiSafe(TEAMS_ENDPOINT, APINbaTeamsResponseDto.class, new APINbaTeamsResponseDto(), LOGGER);
        for (APINbaTeamResponseDto dto : apiNbaTeamsResponseDto.response) {
            transactionManager.doInNewTransaction(() -> {
                try {
                    Optional<TeamData> wrapper = convertService.toEntity(dto);
                    if (wrapper.isPresent()) {
                        TeamData teamData = wrapper.get();
                        if (teamData.isNbaFranchise()) {
                            setTeamStats(SupportedSeasons.getOngoingSeason(), teamData);
                        }
                        String id = teamDataService.update(teamData.getExternalId(), teamData);
                        LOGGER.info(String.format("Entity saved: %s", id));
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
        updateService.update(started, getCollectionName(), new APINbaUpdate(Instant.now(), currentStatus.isWithErrors() ? currentStatus : APINbaUpdate.Status.FINISHED));
        LOGGER.warn("Updating teams has finished.");
    }

    private void setTeamStats(int season, TeamData teamData) {
        APINbaTeamsStatsDto teamStatsDto = externalService.callApiSafe(TEAM_STATS_ENDPOINT.concat("?id=%s&season=%s".formatted(teamData.getExternalId(), season)), APINbaTeamsStatsDto.class, new APINbaTeamsStatsDto(), LOGGER);
        statsConvertService.toEntity(teamStatsDto.response.stream().findFirst().orElse(new APINbaTeamStatsDto())).ifPresent(teamStats -> teamData.getStatsBySeason().put(season, teamStats));
    }

    @Override
    protected String getCollectionName() {
        return "teams";
    }
}
