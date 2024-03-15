package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaResponseDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APINbaTeamsResponseDto extends APINbaResponseDto<APINbaTeamResponseDto> {
    @Override
    public String toString() {
        return "APINbaTeamsResponseDto{" +
                "get='" + get + '\'' +
                ", results=" + results +
                ", response=" + response.toString() +
                '}';
    }
}
