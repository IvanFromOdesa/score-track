package com.teamk.scoretrack.module.core.api.nbaapi.commons.service;

import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentMetadata;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentsMetadata;
import com.teamk.scoretrack.module.core.api.commons.sport_components.service.ISportComponentMetadataService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.convert.APINbaComponentMetadataConvertService;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class APINbaSportComponentMetadataService implements ISportComponentMetadataService {
    private final APINbaUpdateEntityService updateEntityService;
    private final APINbaComponentMetadataConvertService convertService;

    @Autowired
    public APINbaSportComponentMetadataService(APINbaUpdateEntityService updateEntityService,
                                               APINbaComponentMetadataConvertService convertService) {
        this.updateEntityService = updateEntityService;
        this.convertService = convertService;
    }

    @Override
    public SportComponentsMetadata collect() {
        return new SportComponentsMetadata(updateEntityService.getDistinctCollectionsStatuses().stream().map(convertService::toDto).collect(Collectors.toMap(SportComponentMetadata::getName, Function.identity())));
    }

    @Override
    public int getApiCode() {
        return SportAPI.API_NBA.getCode();
    }
}
