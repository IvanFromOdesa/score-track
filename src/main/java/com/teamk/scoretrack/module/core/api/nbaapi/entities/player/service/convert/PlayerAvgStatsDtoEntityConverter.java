package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert;

import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerAvgStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerAvgStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.scheduler.PlayerSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlayerAvgStatsDtoEntityConverter extends DtoEntityConvertService<PlayerAvgStats, APINbaPlayerAvgStatsDto> {
    public PlayerAvgStatsDtoEntityConverter(MappingContext<PlayerAvgStats, APINbaPlayerAvgStatsDto> mappingContext) {
        super(mappingContext, DtoEntityValidator.withDefaults());
    }

    @Override
    protected Logger resolveLogger() {
        return LoggerFactory.getLogger(PlayerSchedulerService.class);
    }
}
