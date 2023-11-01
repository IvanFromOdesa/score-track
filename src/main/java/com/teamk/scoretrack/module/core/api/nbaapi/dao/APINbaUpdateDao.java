package com.teamk.scoretrack.module.core.api.nbaapi.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoDao;
import com.teamk.scoretrack.module.core.api.nbaapi.domain.APINbaUpdate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface APINbaUpdateDao extends MongoDao<APINbaUpdate> {
    Optional<APINbaUpdate> findFirstByCollectionNameOrderByFinishedDesc(String collectionName);
    Optional<APINbaUpdate> findFirstByStarted(Instant started);
}
