package com.teamk.scoretrack.module.core.api.nbaapi.commons.dao;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdateMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class APINbaUpdateDaoImpl implements APINbaUpdateMongoProjection {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public APINbaUpdateDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Collection<APINbaUpdateMetadata> fetchCollection() {
        GroupOperation groupOperation = groupByCollectionName();
        Aggregation aggregation = Aggregation.newAggregation(groupOperation);
        AggregationResults<APINbaUpdateMetadata> res = mongoTemplate.aggregate(aggregation, APINbaUpdate.class, APINbaUpdateMetadata.class);
        return res.getMappedResults();
    }

    private static GroupOperation groupByCollectionName() {
        return Aggregation.group("collectionName")
                .last("collectionName").as("name")
                .last("started").as("updated")
                .last("status").as("status")
                .count().as("updateCount");
    }
}
