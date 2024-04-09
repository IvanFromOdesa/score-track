package com.teamk.scoretrack.module.core.api.nbaapi.commons.service;

import com.google.common.collect.ImmutableSet;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dao.APINbaUpdateDao;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdateMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
public class APINbaUpdateEntityService extends AbstractMongoEntityService<APINbaUpdate, APINbaUpdateDao> {

    public String update(Instant started, String collectionName, APINbaUpdate apiNbaUpdate) {
        return update(apiNbaUpdate, () -> dao.findFirstByStartedAndCollectionName(started, collectionName));
    }

    public boolean isUpdatePossible(String collectionName) {
        Optional<APINbaUpdate> update = dao.findTopByCollectionNameOrderByFinishedDesc(collectionName);
        return update.isEmpty() || update.get().getStatus().isFinished();
    }

    public Set<APINbaUpdateMetadata> getDistinctCollectionsStatuses() {
        return ImmutableSet.copyOf(dao.fetchCollection());
    }

    @Override
    @Autowired
    protected void setDao(APINbaUpdateDao dao) {
        this.dao = dao;
    }
}
