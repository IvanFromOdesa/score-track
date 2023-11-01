package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service;

import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dao.TeamDao;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamDataEntityService extends AbstractMongoEntityService<TeamData, TeamDao> {
    @Override
    public String update(String id, TeamData teamData) {
        return update(id, teamData, p -> dao.findByExternalId(p));
    }

    /*@Override
    public Class<TeamData> getDomainClass() {
        return TeamData.class;
    }*/

    @Override
    @Autowired
    protected void setDao(TeamDao dao) {
        this.dao = dao;
    }
}
