package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.scheduler;

import com.teamk.scoretrack.module.commons.exception.BaseErrorMapException;
import com.teamk.scoretrack.module.commons.mongo.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.base.service.BaseTransactionManager;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.core.api.nbaapi.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamsResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.TeamDataEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert.TeamDtoEntityConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.service.scheduler.APINbaSchedulerService;
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
    protected final Logger LOGGER;
    private static final String TEAMS_ENDPOINT = "/teams";

    public TeamSchedulerService(@Qualifier("nbaapiTeamMappingContext") MappingContext<TeamData, APINbaTeamResponseDto> mappingContext,
                                DtoEntityValidator<APINbaTeamResponseDto, FormValidationContext<APINbaTeamResponseDto>> validator, TeamDataEntityService teamDataService, BaseTransactionManager transactionManager) {
        this.teamDataService = teamDataService;
        this.transactionManager = transactionManager;
        this.convertService = new TeamDtoEntityConvertService(mappingContext, validator);
        LOGGER = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void startUpdate() {
        LOGGER.info("Updating teams has started.");
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
                        String id = teamDataService.update(teamData.getExternalId(), teamData);
                        LOGGER.info(String.format("Entity saved: %s", id));
                    }
                } catch (BaseErrorMapException e) {
                    updateStatus.set(APINbaUpdate.Status.WITH_ERRORS);
                    LOGGER.error(String.format("TD form: %s", dto));
                    LOGGER.error(String.format("Errors: %s", e.getErrorMap().getErrors().toString()));
                }
            });
        }
        APINbaUpdate.Status currentStatus = updateStatus.get();
        updateService.update(started, new APINbaUpdate(Instant.now(), currentStatus.isWithErrors() ? currentStatus : APINbaUpdate.Status.FINISHED));
        LOGGER.info("Updating teams has finished.");
    }

    @Override
    protected String getCollectionName() {
        return "teams";
    }
}
