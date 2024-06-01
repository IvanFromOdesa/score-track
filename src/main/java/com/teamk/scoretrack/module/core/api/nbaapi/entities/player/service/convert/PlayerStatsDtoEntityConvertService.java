package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert;

import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.scheduler.PlayerSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatsDtoEntityConvertService extends DtoEntityConvertService<PlayerStats, APINbaPlayerStatsDto> {
    public PlayerStatsDtoEntityConvertService(MappingContext<PlayerStats, APINbaPlayerStatsDto> mappingContext) {
        super(mappingContext, DtoEntityValidator.withDefaults());
    }

    @Override
    protected Logger resolveLogger() {
        return LoggerFactory.getLogger(PlayerSchedulerService.class);
    }
}
