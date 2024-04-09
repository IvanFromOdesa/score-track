package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao;

import com.teamk.scoretrack.module.commons.mongo.dao.MongoDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamDao extends MongoDao<TeamData>, TeamDaoMongoProjection {
    Optional<TeamData> findByExternalId(String id);

    /**
     * Load only the necessary fields, exclude stats by default
     */
    @Query(value = "{allStar: false, nbaFranchise: true}", fields = "{statsBySeason: 0}")
    Page<TeamData> findAllWithNbaFranchise(Pageable pageable);
}
