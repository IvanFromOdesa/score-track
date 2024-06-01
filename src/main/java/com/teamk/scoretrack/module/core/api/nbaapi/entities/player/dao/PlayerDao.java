package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dao;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dao.APINbaCommonDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDao extends APINbaCommonDao<PlayerData>, PlayerDaoMongoProjection {
}
