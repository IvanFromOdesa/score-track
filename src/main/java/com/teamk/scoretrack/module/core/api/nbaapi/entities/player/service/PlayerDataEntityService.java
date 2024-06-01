package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service;

import com.google.common.collect.ImmutableList;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dao.PlayerDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dao.PlayerDaoImpl;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PlayerDataEntityService extends AbstractMongoEntityService<PlayerData, PlayerDao> {
    @Override
    public void updateAll(Collection<PlayerData> playerData) {
        super.updateAll(playerData, e -> dao.findByExternalId(e.getExternalId()));
    }

    @Cacheable(cacheNames = CacheStore.ApiNba.TOP_PLAYERS_BY_SEASON, key = "{#type.name(), #season, #playerCount}")
    public List<PlayerDataLeaderboardProjection> getLeaderboardPlayers(PlayerDataStatCategoryInfoHelper type, int season, int playerCount) {
        Collection<PlayerDataLeaderboardProjection> result = new ArrayList<>();
        if (type.isEfficiency()) {
            result.addAll(dao.findTopEfficiencyPlayers(new PlayerDaoImpl.Options(season, playerCount)));
        } else {
            result.addAll(dao.findTopByTypePlayers(new PlayerDaoImpl.Options(season, playerCount), type));
        }
        return ImmutableList.copyOf(result);
    }

    @Override
    @Autowired
    protected void setDao(PlayerDao dao) {
        this.dao = dao;
    }
}
