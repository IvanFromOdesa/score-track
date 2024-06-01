package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dao;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataFullNameProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class PlayerDaoImpl implements PlayerDaoMongoProjection {
    private final MongoTemplate mongotemplate;

    @Autowired
    public PlayerDaoImpl(MongoTemplate mongotemplate) {
        this.mongotemplate = mongotemplate;
    }

    @Override
    public Collection<PlayerDataLeaderboardProjection> findTopEfficiencyPlayers(Options options) {
        String season = "_" + options.season;
        ProjectionOperation projectPlayerData = projectPlayerData(season)
                .andExpression(getNumerator(season)).as("numerator")
                .andExpression(getDenominator(season)).as("denominator");
        GroupOperation groupByExternalId = groupByExternalId(season)
                .avg(ArithmeticOperators.Subtract.valueOf("numerator")
                        .subtract("denominator"))
                .as("valueAvg");
        Aggregation aggregation = getAggregation(options, season, projectPlayerData, groupByExternalId);
        return aggregate(aggregation, PlayerDataLeaderboardProjection.class).getMappedResults();
    }

    @Override
    public Collection<PlayerDataLeaderboardProjection> findTopByTypePlayers(Options options, PlayerDataStatCategoryInfoHelper type) {
        return getPlayerDataLeaderboardProjections(options, type.getStatName());
    }

    private List<PlayerDataLeaderboardProjection> getPlayerDataLeaderboardProjections(Options options, String onStat) {
        String season = "_" + options.season;
        ProjectionOperation points = projectPlayerData(season).and("statsBySeason." + season + "." + onStat).as(onStat);
        GroupOperation groupByExternalId = groupByExternalId(season).avg(onStat).as("valueAvg");
        Aggregation aggregation = getAggregation(options, season, points, groupByExternalId);
        return aggregate(aggregation, PlayerDataLeaderboardProjection.class).getMappedResults();
    }

    @Override
    public Optional<PlayerDataFullNameProjection> findByFullName(String fullName) {
        Aggregation aggregation = newAggregation(
                project()
                        .and("externalId").as("externalId")
                        .andExpression("concat(firstName, ' ', lastName)")
                        .as("fullName"),
                match(Criteria.where("fullName").is(fullName))
        );
        PlayerDataFullNameProjection res = aggregate(aggregation, PlayerDataFullNameProjection.class).getUniqueMappedResult();
        return Optional.ofNullable(res);
    }

    private <R> AggregationResults<R> aggregate(Aggregation aggregation, Class<R> projectionClass) {
        return mongotemplate.aggregate(aggregation, "players", projectionClass);
    }

    private static Aggregation getAggregation(Options options, String season, ProjectionOperation projectPlayerData, GroupOperation groupByExternalId) {
        return newAggregation(
                unwind("statsBySeason." + season),
                projectPlayerData,
                groupByExternalId,
                joinTeamData(),
                projectWithTeamData(),
                sort(Sort.Direction.DESC, "valueAvg"),
                group().push(ROOT).as("players"),
                addRankField(),
                unwind("$players"),
                replaceRoot("$players"),
                limit(options.playerCount)
        );
    }

    private static AddFieldsOperation addRankField() {
        return addFields().addField("players")
                .withValueOf(
                        (AggregationExpression) context -> new Document("$map",
                                new Document("input", "$players")
                                        .append("as", "player")
                                        .append("in", new Document("$mergeObjects",
                                                        Arrays.asList(
                                                                "$$player",
                                                                new Document("rank", new Document("$add",
                                                                        Arrays.asList(
                                                                                new Document("$indexOfArray",
                                                                                        Arrays.asList("$players.valueAvg", "$$player.valueAvg")),
                                                                                1
                                                                        )
                                                                ))
                                                        )
                                                )
                                        )
                        )
                ).build();
    }

    private static ProjectionOperation projectWithTeamData() {
        return project()
                .and("firstName").as("firstName")
                .and("lastName").as("lastName")
                .and("externalId").as("externalId")
                .and("valueAvg").as("valueAvg")
                .and("team.name").as("teamName")
                .and("team.logo").as("teamLogo")
                .and("team.externalId").as("teamExternalId")
                .and("team.code").as("teamCode");
    }

    private static LookupOperation joinTeamData() {
        return lookup("teams", "teamId", "_id", "team");
    }

    private static GroupOperation groupByExternalId(String season) {
        return group(Fields.fields("externalId"))
                .last("externalId").as("externalId")
                .last("firstName").as("firstName")
                .last("lastName").as("lastName")
                .last("teamBySeason." + season).as("teamId");
    }

    private static ProjectionOperation projectPlayerData(String season) {
        return project()
                .and("firstName").as("firstName")
                .and("lastName").as("lastName")
                .and("externalId").as("externalId")
                .and("teamBySeason." + season).as("teamBySeason." + season);
    }

    private static String getDenominator(String season) {
        return "statsBySeason." + season + ".fga - statsBySeason." + season + ".fgm + statsBySeason." + season + ".fta - statsBySeason." + season + ".ftm + statsBySeason." + season + ".turnovers";
    }

    private static String getNumerator(String season) {
        return "statsBySeason." + season + ".points + statsBySeason." + season + ".totReb + statsBySeason." + season + ".assists + statsBySeason." + season + ".steals + statsBySeason." + season + ".blocks";
    }

    public record Options(int season, int playerCount) {

    }
}
