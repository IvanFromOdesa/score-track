package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service;

import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao.TeamDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao.TeamDaoImpl;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TeamDataEntityService extends AbstractMongoEntityService<TeamData, TeamDao> {
    @Override
    public String update(String id, TeamData teamData) {
        return update(teamData, () -> dao.findByExternalId(id));
    }

    @Override
    public Page<TeamData> getAll(int page, int size, String direction, String... sortBys) {
        return dao.findAllWithNbaFranchise(getPageable(page, size, direction, sortBys));
    }

    public Map<Integer, TeamStats> getTeamStats(String externalId) {
        return dao.fetchMap(new TeamDaoImpl.Options(externalId));
    }

    @Override
    @Autowired
    protected void setDao(TeamDao dao) {
        this.dao = dao;
    }
}
