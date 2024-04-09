package com.teamk.scoretrack.module.core.api.commons.init.dto;

import com.teamk.scoretrack.module.core.api.commons.base.BundleResponse;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.ApiSportComponentsMetadata;

public final class InitResponse extends BundleResponse {
    private UserDataDto userData;
    private ApiSportComponentsMetadata apiSportComponentsMetadata;

    public UserDataDto getUserData() {
        return userData;
    }

    public void setUserData(UserDataDto userData) {
        this.userData = userData;
    }

    public ApiSportComponentsMetadata getApiSportComponentsMetadata() {
        return apiSportComponentsMetadata;
    }

    public void setApiSportComponentsMetadata(ApiSportComponentsMetadata apiSportComponentsMetadata) {
        this.apiSportComponentsMetadata = apiSportComponentsMetadata;
    }
}