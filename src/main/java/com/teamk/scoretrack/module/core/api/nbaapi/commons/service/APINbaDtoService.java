package com.teamk.scoretrack.module.core.api.nbaapi.commons.service;

import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoDtoService;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaIdentifier;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class APINbaDtoService<ENTITY extends APINbaIdentifier, DTO, ENTITY_SERVICE extends AbstractMongoEntityService<ENTITY, ?>> extends AbstractMongoDtoService<ENTITY, DTO, ENTITY_SERVICE> {
    @Autowired
    protected APINbaUiHintService uiHintService;
}
