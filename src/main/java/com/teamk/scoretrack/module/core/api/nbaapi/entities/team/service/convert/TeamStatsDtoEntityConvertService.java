package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert;

import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.scheduler.TeamSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TeamStatsDtoEntityConvertService extends DtoEntityConvertService<TeamStats, APINbaTeamStatsDto> {
    public TeamStatsDtoEntityConvertService(MappingContext<TeamStats, APINbaTeamStatsDto> mappingContext) {
        super(mappingContext, DtoEntityValidator.withDefaults());
    }

    @Override
    protected Logger resolveLogger() {
        return LoggerFactory.getLogger(TeamSchedulerService.class);
    }
}
