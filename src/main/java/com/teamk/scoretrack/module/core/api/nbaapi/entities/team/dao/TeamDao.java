package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamDao extends MongoDao<TeamData> {
    Optional<TeamData> findByExternalId(String id);
}
