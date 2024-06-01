package com.teamk.scoretrack.module.core.api.nbaapi.commons.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoDao;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaIdentifier;

import java.util.Optional;

public interface APINbaCommonDao<E extends APINbaIdentifier> extends MongoDao<E> {
    Optional<E> findByExternalId(String id);
}
