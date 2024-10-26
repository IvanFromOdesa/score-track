package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dao;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.Stats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerAvgStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataFullNameProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class PlayerDaoImpl implements PlayerDaoMongoProjection {
    private final MongoTemplate mongotemplate;

    @Autowired
    public PlayerDaoImpl(MongoTemplate mongotemplate) {
        this.mongotemplate = mongotemplate;
    }

    @Override
    public Optional<PlayerData> findByExternalId(String id, boolean excludeStatsBySeason) {
        Query query = new Query();
        query.addCriteria(Criteria.where("externalId").is(id));
        if (excludeStatsBySeason) {
            query.fields().exclude("statsBySeason");
        }
        PlayerData playerData = mongotemplate.findOne(query, PlayerData.class);
        return Optional.ofNullable(playerData);
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
        return getPlayerDataLeaderboardProjections(options, type.getFieldName());
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

    @Override
    public Map<SupportedSeasons, PlayerAvgStats> findAvgPlayerStatsPerSeason(String externalId) {
        final String statsBySeason = "statsBySeason";
        final String arrayValue = statsBySeason.concat(".v");
        Aggregation aggregation = newAggregation(
                match(Criteria.where("externalId").is(externalId)),
                project().and(ObjectOperators.valueOf(statsBySeason).toArray()).as(statsBySeason),
                unwind(statsBySeason),
                unwind(arrayValue),
                match(Criteria.where(arrayValue.concat(".minutes")).gt(0)),
                projectStatsObject(arrayValue),
                groupStatsBySeasonAndPosition(),
                groupStatsBySeason(),
                projectStatsPerMinute(),
                groupStatsBySeasonArray(),
                project().andExclude("_id").and(context -> new Document("$arrayToObject", "$statsBySeason")).as("statsBySeason")
        );
        PlayerAvgStatsPerSeason uniqueMappedResult = aggregate(aggregation, PlayerAvgStatsPerSeason.class).getUniqueMappedResult();
        return uniqueMappedResult != null ? uniqueMappedResult.statsBySeason() : Map.of();
    }

    private <R> AggregationResults<R> aggregate(Aggregation aggregation, Class<R> projectionClass) {
        return mongotemplate.aggregate(aggregation, PlayerData.COLLECTION_NAME, projectionClass);
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

    private static ProjectionOperation projectStatsObject(String arrayValue) {
        ProjectionOperation project = project();
        for (String field : Stats.getFieldNames()) {
            project = project.and(arrayValue.concat(".%s".formatted(field))).as(field);
        }
        return project
                .and(arrayValue.concat(".position")).as("position")
                .and(arrayValue.concat(".minutes")).as("minutes")
                .and("statsBySeason.k").as("season");
    }

    private static GroupOperation groupStatsBySeasonAndPosition() {
        GroupOperation group = group(Fields.fields("season", "position"));
        for (String field : Stats.getFieldNames()) {
            group = group.sum(field).as(field);
        }
        return group.sum("minutes").as("minutes").count().as("count");
    }

    private static GroupOperation groupStatsBySeason() {
        GroupOperation group = group("$_id.season");
        for (String field: Stats.getFieldNames()) {
            group = group.sum(field).as(field);
        }
        return group.sum("minutes").as("minutes")
                .sum("count").as("count")
                .push(new Document("position", "$_id.position").append("count", "$count")).as("positions");
    }

    private static ProjectionOperation projectStatsPerMinute() {
        ProjectionOperation project = project();
        List<String> fieldNames = new ArrayList<>(Stats.getFieldNames());
        List<String> percentageFields = List.of("fgp", "ftp", "tpp", "plusMinus");
        fieldNames.removeAll(percentageFields);
        BiFunction<String, String, ArithmeticOperators.Divide> divideFunction = (f, divideBy) -> ArithmeticOperators.Divide.valueOf("$%s".formatted(f)).divideBy(divideBy);
        for (String field: fieldNames) {
            project = project.and(divideFunction.apply(field, "$minutes")).as(field);
        }
        for (String field: percentageFields) {
            project = project.and(divideFunction.apply(field, "$count")).as(field);
        }
        Document filterDocument = new Document("$filter", new Document()
                .append("input", "$positions")
                .append("as", "pos")
                .append("cond", new Document("$eq", Arrays.asList("$$pos.count", new Document("$max", "$positions.count"))))
        );
        Document mapDocument = new Document("$map", new Document()
                .append("input", filterDocument)
                .append("as", "pos")
                .append("in", "$$pos.position")
        );
        Document arrayElemAtDocument = new Document("$arrayElemAt", Arrays.asList(mapDocument, 0));
        return project.and(ex -> arrayElemAtDocument).as("mostCommonPosition");
    }

    private static GroupOperation groupStatsBySeasonArray() {
        Document document = new Document("k", "$_id");
        Document values = new Document();
        for (String field: Stats.getFieldNames()) {
            values = values.append(field, "$%s".formatted(field));
        }
        values = values.append("position", "$mostCommonPosition");
        document = document.append("v", values);
        return group().push(document).as("statsBySeason");
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

    private record PlayerAvgStatsPerSeason(Map<SupportedSeasons, PlayerAvgStats> statsBySeason) {}
}
