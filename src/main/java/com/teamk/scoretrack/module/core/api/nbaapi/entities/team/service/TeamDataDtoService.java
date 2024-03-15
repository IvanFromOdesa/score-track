package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service;

import com.teamk.scoretrack.module.commons.base.page.RestPage;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoDtoService;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoEntityService;
import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TeamDataDtoService extends AbstractMongoDtoService<TeamData, APINbaTeamResponseDto> {
    @Override
    @PreAuthorize("@aclService.checkApiAcl(T(com.teamk.scoretrack.module.core.entities.SportAPI).API_NBA.key)")
    @Cacheable(cacheNames = {CacheStore.ApiNba.TEAM_DATA}, key = "#page")
    public RestPage<APINbaTeamResponseDto> getDtoPage(int page, int size, String... sortBys) {
        return RestPage.of(super.getDtoPage(page, size, sortBys));
    }

    @Override
    protected Page<TeamData> getEntityPage(int page, int size, String[] sortBys) {
        return entityService.getAll(page, size, Example.of(TeamData.withNbaFranchise()), "asc", sortBys);
    }

    @Override
    @Autowired
    protected void setEntityService(AbstractMongoEntityService<TeamData, ?> entityService) {
        this.entityService = entityService;
    }

    @Override
    @Autowired
    protected void setConvertService(DtoEntityConvertService<TeamData, APINbaTeamResponseDto> convertService) {
        this.convertService = convertService;
    }
}
