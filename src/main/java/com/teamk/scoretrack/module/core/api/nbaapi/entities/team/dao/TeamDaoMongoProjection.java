package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public interface TeamDaoMongoProjection extends MongoProjection<TeamStats, TeamDaoImpl.Options> {
    @Override
    default Optional<TeamStats> fetch(TeamDaoImpl.Options options) {
        return Optional.empty();
    }

    @Override
    default Optional<TeamStats> fetch() {
        return Optional.empty();
    }

    @Override
    default Collection<TeamStats> fetchCollection(TeamDaoImpl.Options options) {
        return Collections.emptyList();
    }

    @Override
    default Collection<TeamStats> fetchCollection() {
        return Collections.emptyList();
    }

    Map<SupportedSeasons, TeamStats> fetchMap(TeamDaoImpl.Options options);

    Map<SupportedSeasons, TeamStats> findAvgTeamStatsBySeason();
}
