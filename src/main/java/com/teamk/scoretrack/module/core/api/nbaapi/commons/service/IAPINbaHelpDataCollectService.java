package com.teamk.scoretrack.module.core.api.nbaapi.commons.service;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaHelpData;

public interface IAPINbaHelpDataCollectService {
    APINbaHelpData getHelpData();
    String getComponentName();
}
