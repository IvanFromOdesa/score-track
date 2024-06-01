package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaHelpData;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.IAPINbaHelpDataCollectService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamsHelpData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert.TeamStatsDtoEntityConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamHelpDataCollectService implements IAPINbaHelpDataCollectService {
    private final TeamDataEntityService entityService;
    private final TeamStatsDtoEntityConvertService convertService;

    @Autowired
    public TeamHelpDataCollectService(TeamDataEntityService entityService,
                                      TeamStatsDtoEntityConvertService convertService) {
        this.entityService = entityService;
        this.convertService = convertService;
    }

    @Override
    public APINbaHelpData getHelpData() {
        return new APINbaTeamsHelpData(getAvgTeamStatsBySeason());
    }

    private Map<String, APINbaTeamStatsDto> getAvgTeamStatsBySeason() {
        return entityService.getTeamAvgStatsBySeason().teamStats().entrySet().stream().collect(Collectors.toMap(e -> String.valueOf(e.getKey().getYear()), e -> convertService.toDto(e.getValue())));
    }

    @Override
    public String getComponentName() {
        return TeamData.COLLECTION_NAME;
    }
}
