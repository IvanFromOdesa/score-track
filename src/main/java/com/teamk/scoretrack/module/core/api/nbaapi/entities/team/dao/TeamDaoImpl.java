package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;

@Repository
public class TeamDaoImpl implements TeamDaoMongoProjection {
    private final MongoTemplate mongotemplate;

    @Autowired
    public TeamDaoImpl(MongoTemplate mongotemplate) {
        this.mongotemplate = mongotemplate;
    }

    @Override
    public Map<Integer, TeamStats> fetchMap(Options options) {
        Query query = new Query();
        query.addCriteria(Criteria.where("externalId").is(options.externalId).and("nbaFranchise").is(true));
        query.fields().include("statsBySeason");
        TeamData one = mongotemplate.findOne(query, TeamData.class);
        return one != null ? one.getStatsBySeason() : Collections.emptyMap();
    }

    public record Options(String externalId) {
    }
}
