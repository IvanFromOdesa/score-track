package com.teamk.scoretrack.module.core.api.nbaapi.commons.domain;

import com.teamk.scoretrack.module.commons.mongo.domain.Identifier;
import org.springframework.data.mongodb.core.index.Indexed;

public class APINbaIdentifier extends Identifier {
    @Indexed(unique = true)
    protected String externalId;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
