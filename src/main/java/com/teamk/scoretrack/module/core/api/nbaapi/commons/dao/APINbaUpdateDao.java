package com.teamk.scoretrack.module.core.api.nbaapi.commons.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoDao;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface APINbaUpdateDao extends MongoDao<APINbaUpdate> {
    Optional<APINbaUpdate> findTopByCollectionNameOrderByFinishedDesc(String collectionName);
    Optional<APINbaUpdate> findFirstByStartedAndCollectionName(Instant started, String collectionName);
}
