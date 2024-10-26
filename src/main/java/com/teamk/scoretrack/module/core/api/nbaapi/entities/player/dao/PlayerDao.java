package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dao;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dao.APINbaCommonDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerDao extends APINbaCommonDao<PlayerData>, PlayerDaoMongoProjection {

}
