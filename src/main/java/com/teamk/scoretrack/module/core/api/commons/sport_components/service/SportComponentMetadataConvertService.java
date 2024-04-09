package com.teamk.scoretrack.module.core.api.commons.sport_components.service;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;
import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentMetadata;
import org.slf4j.Logger;

public abstract class SportComponentMetadataConvertService<ENTITY extends IdAware<?>> extends DtoEntityConvertService<ENTITY, SportComponentMetadata> {
    public SportComponentMetadataConvertService(MappingContext<ENTITY, SportComponentMetadata> mappingContext) {
        super(mappingContext, DtoEntityValidator.withDefaults());
    }

    /**
     * @return common logger
     */
    @Override
    protected Logger resolveLogger() {
        return MessageLogger.LOGGER;
    }
}
