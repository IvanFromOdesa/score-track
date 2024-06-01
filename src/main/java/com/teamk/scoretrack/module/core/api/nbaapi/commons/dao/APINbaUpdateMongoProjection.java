package com.teamk.scoretrack.module.core.api.nbaapi.commons.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdateMetadata;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface APINbaUpdateMongoProjection extends MongoProjection<APINbaUpdateMetadata, Void> {
    @Override
    default Optional<APINbaUpdateMetadata> fetch(Void options) {
        return Optional.empty();
    }

    @Override
    default Optional<APINbaUpdateMetadata> fetch() {
        return Optional.empty();
    }

    @Override
    default Collection<APINbaUpdateMetadata> fetchCollection(Void options) {
        return Collections.emptyList();
    }

    List<SupportedSeasons> findAvailableSeasonsForCollection(String name);
}
