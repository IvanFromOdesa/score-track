package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaResponseDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APINbaPlayersResponseDto extends APINbaResponseDto<APINbaPlayerResponseDto> {
    @Override
    public String toString() {
        return "APINbaPlayersResponseDto{" +
                "get='" + get + '\'' +
                ", results=" + results +
                ", response=" + response.toString() +
                '}';
    }
}
