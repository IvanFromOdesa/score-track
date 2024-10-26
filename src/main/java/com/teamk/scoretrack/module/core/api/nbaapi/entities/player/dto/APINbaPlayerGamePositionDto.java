package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.teamk.scoretrack.module.core.api.commons.base.UiWrapperResponse;

public class APINbaPlayerGamePositionDto extends UiWrapperResponse<String> {
    @JsonCreator
    public APINbaPlayerGamePositionDto(String value) {
        super(value, null);
    }

    public APINbaPlayerGamePositionDto(String value, String uiText) {
        super(value, uiText);
    }
}
