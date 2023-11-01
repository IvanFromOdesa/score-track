package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert;

import com.teamk.scoretrack.module.commons.mongo.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.mongo.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.scheduler.TeamSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TeamDtoEntityConvertService extends DtoEntityConvertService<TeamData, APINbaTeamResponseDto> {
    public TeamDtoEntityConvertService(MappingContext<TeamData, APINbaTeamResponseDto> mappingContext, DtoEntityValidator<APINbaTeamResponseDto, FormValidationContext<APINbaTeamResponseDto>> validator) {
        super(mappingContext, validator);
    }

    @Override
    protected Logger resolveLogger() {
        return LoggerFactory.getLogger(TeamSchedulerService.class);
    }
}
