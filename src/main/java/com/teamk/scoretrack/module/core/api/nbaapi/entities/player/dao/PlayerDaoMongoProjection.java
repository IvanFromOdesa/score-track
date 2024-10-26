package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerAvgStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataFullNameProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dao.PlayerDaoImpl.Options;

public interface PlayerDaoMongoProjection extends MongoProjection<PlayerData, Options> {
    @Override
    default Optional<PlayerData> fetch(Options options) {
        return Optional.empty();
    }

    @Override
    default Optional<PlayerData> fetch() {
        return Optional.empty();
    }

    @Override
    default Collection<PlayerData> fetchCollection(Options options) {
        return Collections.emptyList();
    }

    @Override
    default Collection<PlayerData> fetchCollection() {
        return Collections.emptyList();
    }

    Optional<PlayerData> findByExternalId(String id, boolean excludeStatsBySeason);

    Collection<PlayerDataLeaderboardProjection> findTopEfficiencyPlayers(Options options);

    Collection<PlayerDataLeaderboardProjection> findTopByTypePlayers(Options options, PlayerDataStatCategoryInfoHelper type);

    Optional<PlayerDataFullNameProjection> findByFullName(String fullName);

    Map<SupportedSeasons, PlayerAvgStats> findAvgPlayerStatsPerSeason(String externalId);
}
