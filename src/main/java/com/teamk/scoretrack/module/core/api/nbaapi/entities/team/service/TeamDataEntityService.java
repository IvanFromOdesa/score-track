package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service;

import com.teamk.scoretrack.module.commons.base.page.RestPage;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao.TeamDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao.TeamDaoImpl;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStatsMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.teamk.scoretrack.module.commons.cache.CacheStore.ApiNba.*;

@Service
public class TeamDataEntityService extends AbstractMongoEntityService<TeamData, TeamDao> {
    @Override
    public String update(String id, TeamData teamData) {
        return update(teamData, () -> dao.findByExternalId(id));
    }

    /**
     * Clear cache when successfully updating to serve fresh data
     * @param teamData
     */
    @Override
    @CacheEvict(cacheNames = {TEAM_DATA, TEAM_STATS, TEAM_STATS_AVG_BY_SEASON}, allEntries = true)
    public void updateAll(Collection<TeamData> teamData) {
        super.updateAll(teamData, e -> dao.findByExternalId(e.getExternalId()));
    }

    public List<TeamData> getAllAsList(int page, int size) {
        return dao.getAllWithNbaFranchise(getPageable(page, size));
    }

    public int getNbaFranchiseCount() {
        return dao.countAllByNbaFranchiseTrueAndAllStarFalse();
    }

    @Override
    @Cacheable(cacheNames = {TEAM_DATA}, key = "#page", unless = "#page > 2")
    public Page<TeamData> getAll(int page, int size, String direction, String... sortBys) {
        return RestPage.of(dao.findAllWithNbaFranchise(getPageable(page, size, direction, sortBys)));
    }

    @Cacheable(cacheNames = {TEAM_STATS}, key = "#externalId")
    public TeamStatsMap getTeamStats(String externalId) {
        return new TeamStatsMap(dao.fetchMap(new TeamDaoImpl.Options(externalId)));
    }

    @Cacheable(cacheNames = {TEAM_STATS_AVG_BY_SEASON})
    public TeamStatsMap getTeamAvgStatsBySeason() {
        return new TeamStatsMap(dao.findAvgTeamStatsBySeason());
    }

    @Override
    @Autowired
    protected void setDao(TeamDao dao) {
        this.dao = dao;
    }
}
