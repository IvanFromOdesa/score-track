package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert;

import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerLeaderboardDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class PlayerEfficiencyEntityDtoConvertService extends DtoEntityConvertService<PlayerDataLeaderboardProjection, APINbaPlayerLeaderboardDto> {
    public PlayerEfficiencyEntityDtoConvertService(MappingContext<PlayerDataLeaderboardProjection, APINbaPlayerLeaderboardDto> mappingContext) {
        super(mappingContext, DtoEntityValidator.withDefaults());
    }

    @Override
    protected Logger resolveLogger() {
        return MessageLogger.LOGGER;
    }
}
