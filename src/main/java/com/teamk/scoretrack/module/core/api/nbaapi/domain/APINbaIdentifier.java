package com.teamk.scoretrack.module.core.api.nbaapi.domain;

import com.teamk.scoretrack.module.commons.mongo.domain.Identifier;

public class APINbaIdentifier extends Identifier {
    protected String externalId;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
