package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.AvgStatsDto;

public class APINbaPlayerAvgStatsDto extends AvgStatsDto {
    private APINbaPlayerGamePositionDto position;

    public APINbaPlayerGamePositionDto getPosition() {
        return position;
    }

    public void setPosition(APINbaPlayerGamePositionDto position) {
        this.position = position;
    }
}
