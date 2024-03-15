package com.teamk.scoretrack.module.core.api.nbaapi.commons.service;

import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dao.APINbaUpdateDao;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class APINbaUpdateEntityService extends AbstractMongoEntityService<APINbaUpdate, APINbaUpdateDao> {

    public String update(Instant started, String collectionName, APINbaUpdate apiNbaUpdate) {
        return update(started, apiNbaUpdate, s -> dao.findFirstByStartedAndCollectionName(s, collectionName));
    }

    public boolean isUpdatePossible(String collectionName) {
        Optional<APINbaUpdate> update = dao.findTopByCollectionNameOrderByFinishedDesc(collectionName);
        return update.isEmpty() || update.get().getStatus().isFinished();
    }

    /*@Override
    public Class<APINbaUpdate> getDomainClass() {
        return APINbaUpdate.class;
    }*/

    @Override
    @Autowired
    protected void setDao(APINbaUpdateDao dao) {
        this.dao = dao;
    }
}
