package com.teamk.scoretrack.module.core.api.nbaapi.commons.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdateMetadata;

import java.util.Collection;
import java.util.Collections;
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
}
