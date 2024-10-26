package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.teamk.scoretrack.module.core.api.commons.base.UiWrapperResponse;

public class APINbaPlayerPositionDto extends UiWrapperResponse<String> {
    @JsonCreator
    public APINbaPlayerPositionDto(String value) {
        super(value, null);
    }

    public APINbaPlayerPositionDto(String value, String uiText) {
        super(value, uiText);
    }
}
