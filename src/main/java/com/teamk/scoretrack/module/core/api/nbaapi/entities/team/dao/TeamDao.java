package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dao.APINbaCommonDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Repository
public interface TeamDao extends APINbaCommonDao<TeamData>, TeamDaoMongoProjection {
    @GetNba
    Page<TeamData> findAllWithNbaFranchise(Pageable pageable);

    @GetNba
    List<TeamData> getAllWithNbaFranchise(Pageable pageable);

    int countAllByNbaFranchiseTrueAndAllStarFalse();

    /**
     * Load only the necessary fields, exclude stats by default
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Query(value = "{allStar: false, nbaFranchise: true}", fields = "{statsBySeason: 0}")
    @interface GetNba {

    }
}
