package com.teamk.scoretrack.module.core.api.nbaapi.commons.dto;

import com.teamk.scoretrack.module.core.api.commons.base.UiWrapperResponse;

public final class APINbaStatCategoryDto extends UiWrapperResponse<String> {
    public APINbaStatCategoryDto(String value, String uiText) {
        super(value, uiText);
    }
}
