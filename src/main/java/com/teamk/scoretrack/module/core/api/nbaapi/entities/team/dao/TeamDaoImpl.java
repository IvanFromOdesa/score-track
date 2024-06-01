package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ObjectOperators;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class TeamDaoImpl implements TeamDaoMongoProjection {
    private final MongoTemplate mongotemplate;

    @Autowired
    public TeamDaoImpl(MongoTemplate mongotemplate) {
        this.mongotemplate = mongotemplate;
    }

    @Override
    public Map<SupportedSeasons, TeamStats> fetchMap(Options options) {
        Query query = new Query();
        query.addCriteria(Criteria.where("externalId").is(options.externalId).and("nbaFranchise").is(true));
        query.fields().include("statsBySeason");
        TeamData one = mongotemplate.findOne(query, TeamData.class);
        return one != null ? one.getStatsBySeason() : Collections.emptyMap();
    }

    public Map<SupportedSeasons, TeamStats> findAvgTeamStatsBySeason() {
        final String statsBySeason = "statsBySeason";
        final String arrayValue = statsBySeason.concat(".v");
        Aggregation aggregation = newAggregation(
                project().and(ObjectOperators.valueOf(statsBySeason).toArray()).as(statsBySeason),
                unwind(statsBySeason),
                groupByKey(arrayValue),
                getProjectionOperation(),
                group().addToSet(new Document("k", "$season").append("v", "$avgStats")).as("avgBySeason"),
                project().andExclude("_id").and(context -> new Document("$arrayToObject", "$avgBySeason")).as("avgBySeason")
        );
        AvgTeamStatsBySeason result = mongotemplate.aggregate(aggregation, TeamData.class, AvgTeamStatsBySeason.class).getUniqueMappedResult();
        return result == null ? Map.of() : result.avgBySeason();
    }

    private static ProjectionOperation getProjectionOperation() {
        ProjectionOperation projectToArray = project().andExclude("_id").and("$_id").as("season");
        for (String field: getFieldNames()) {
            projectToArray = projectToArray.and(field).as("avgStats." + field);
        }
        return projectToArray;
    }

    private static GroupOperation groupByKey(String arrayValue) {
        GroupOperation group = group("statsBySeason".concat(".k"));
        for (String field : getFieldNames()) {
            group = group.avg(arrayValue.concat(".%s").formatted(field)).as(field);
        }
        return group;
    }

    private static List<String> getFieldNames() {
        return List.of(
                "games",
                "fastBreakPoints",
                "pointsInPaint",
                "biggestLead",
                "secondChancePoints",
                "pointsOffTurnovers",
                "longestRun",
                "points",
                "fgm",
                "fga",
                "fgp",
                "ftm",
                "fta",
                "ftp",
                "tpm",
                "tpa",
                "tpp",
                "offReb",
                "defReb",
                "totReb",
                "assists",
                "pFouls",
                "steals",
                "turnovers",
                "blocks",
                "plusMinus"
        );
    }

    public record Options(String externalId) {

    }

    private record AvgTeamStatsBySeason(Map<SupportedSeasons, TeamStats> avgBySeason) {

    }
}
