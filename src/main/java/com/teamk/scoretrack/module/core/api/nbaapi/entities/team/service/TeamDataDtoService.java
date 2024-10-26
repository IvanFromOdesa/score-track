package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service;

import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.mongo.service.AbstractMongoDtoService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.util.SupportedSeasonsUtils;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.NbaTeamInfoHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsMapDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert.TeamStatsDtoEntityConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.i18n.APINbaTeamInfoHelperTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@PreAuthorize("hasApiAccess(T(com.teamk.scoretrack.module.core.entities.sport_api.SportAPI).API_NBA.key) && @APINbaUpdateEntityService.isAccessible('teams')")
public class TeamDataDtoService extends AbstractMongoDtoService<TeamData, APINbaTeamResponseDto, TeamDataEntityService> {
    private final TeamStatsDtoEntityConvertService statsDtoEntityConvertService;
    private final APINbaTeamInfoHelperTranslatorService infoHelperTranslatorService;

    @Autowired
    public TeamDataDtoService(TeamStatsDtoEntityConvertService statsDtoEntityConvertService,
                              APINbaTeamInfoHelperTranslatorService infoHelperTranslatorService) {
        this.statsDtoEntityConvertService = statsDtoEntityConvertService;
        this.infoHelperTranslatorService = infoHelperTranslatorService;
    }

    // PreAuthorize does not protect parent methods
    @Override
    public Page<APINbaTeamResponseDto> getDtoPage(int page, int size, String... sortBys) {
        return super.getDtoPage(page, size, sortBys);
    }

    public APINbaTeamStatsMapDto getTeamStats(String externalId, String teamCode) {
        Map<SupportedSeasons, TeamStats> teamStats = entityService.getTeamStats(externalId).teamStats();
        NbaTeamInfoHelper byCode = NbaTeamInfoHelper.getByCode(teamCode);
        SupportedSeasonsDto[] seasons = SupportedSeasonsUtils.transformToArrayDto(teamStats.keySet());
        return new APINbaTeamStatsMapDto(seasons, toStatsDto(teamStats), byCode, infoHelperTranslatorService.getMessage(teamCode));
    }

    private Map<String, APINbaTeamStatsDto> toStatsDto(Map<SupportedSeasons, TeamStats> data) {
        return data.entrySet().stream().collect(Collectors.toMap(e -> String.valueOf(e.getKey().getYear()), e -> statsDtoEntityConvertService.toDto(e.getValue())));
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
