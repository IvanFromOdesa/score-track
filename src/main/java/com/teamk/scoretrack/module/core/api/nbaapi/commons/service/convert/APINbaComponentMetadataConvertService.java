package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.convert;

import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentMetadata;
import com.teamk.scoretrack.module.core.api.commons.sport_components.service.SportComponentMetadataConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdateMetadata;
import org.springframework.stereotype.Service;

@Service
public class APINbaComponentMetadataConvertService extends SportComponentMetadataConvertService<APINbaUpdateMetadata> {
    public APINbaComponentMetadataConvertService(MappingContext<APINbaUpdateMetadata, SportComponentMetadata> mappingContext) {
        super(mappingContext);
    }
}
