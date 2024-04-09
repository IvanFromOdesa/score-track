package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service;

import com.teamk.scoretrack.module.commons.base.page.RestPage;
import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoDtoService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsMapDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert.TeamStatsDtoEntityConvertService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@PreAuthorize("hasApiAccess(T(com.teamk.scoretrack.module.core.entities.SportAPI).API_NBA.key)")
public class TeamDataDtoService extends AbstractMongoDtoService<TeamData, APINbaTeamResponseDto, TeamDataEntityService> {
    private final TeamStatsDtoEntityConvertService statsDtoEntityConvertService;

    @Autowired
    public TeamDataDtoService(TeamStatsDtoEntityConvertService statsDtoEntityConvertService) {
        this.statsDtoEntityConvertService = statsDtoEntityConvertService;
    }

    @Override
    @Cacheable(cacheNames = {CacheStore.ApiNba.TEAM_DATA}, key = "#page", unless = "#page > 2")
    public RestPage<APINbaTeamResponseDto> getDtoPage(int page, int size, String... sortBys) {
        return RestPage.of(super.getDtoPage(page, size, sortBys));
    }

    @Cacheable(cacheNames = {CacheStore.ApiNba.TEAM_STATS}, key = "#externalId")
    public APINbaTeamStatsMapDto getTeamStats(String externalId) {
        Map<Integer, TeamStats> teamStats = entityService.getTeamStats(externalId);
        return new APINbaTeamStatsMapDto(toStatsDto(teamStats), teamStats.keySet().toArray(Integer[]::new));
    }

    @NotNull
    private Map<Integer, APINbaTeamStatsDto> toStatsDto(Map<Integer, TeamStats> data) {
        return data.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> statsDtoEntityConvertService.toDto(e.getValue())));
    }

    @Override
    @Autowired
    protected void setEntityService(TeamDataEntityService entityService) {
        this.entityService = entityService;
    }

    @Override
    @Autowired
    protected void setConvertService(DtoEntityConvertService<TeamData, APINbaTeamResponseDto> convertService) {
        this.convertService = convertService;
    }
}
