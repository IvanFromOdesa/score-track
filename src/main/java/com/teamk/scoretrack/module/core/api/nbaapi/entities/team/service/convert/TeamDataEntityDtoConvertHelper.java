package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;

import java.util.Map;
import java.util.stream.Collectors;

public class TeamDataEntityDtoConvertHelper {
    public static Map<String, APINbaTeamResponseDto.League> getLeagueWrapper(Map<String, TeamData.League> source) {
        return source.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> {
            APINbaTeamResponseDto.League league = new APINbaTeamResponseDto.League();
            TeamData.League lg = e.getValue();
            league.division = lg.division();
            league.conference = lg.conference();
            return league;
        }));
    }

    public static Map<String, TeamData.League> getLeagueMap(Map<String, APINbaTeamResponseDto.League> source) {
        return source.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> {
            APINbaTeamResponseDto.League league = e.getValue();
            return new TeamData.League(league.conference, league.division);
        }));
    }
}
