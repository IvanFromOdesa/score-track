package com.teamk.scoretrack.module.core.api.commons.init.dto;

import com.teamk.scoretrack.module.core.api.commons.base.dto.BundleResponse;
import com.teamk.scoretrack.module.core.api.commons.base.dto.UserDataDto;

public final class InitResponse extends BundleResponse {
    private UserDataDto userData;

    public UserDataDto getUserData() {
        return userData;
    }

    public void setUserData(UserDataDto userData) {
        this.userData = userData;
    }
}