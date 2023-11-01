package com.teamk.scoretrack.module.core.api.nbaapi.service.convert;

import com.teamk.scoretrack.module.commons.mongo.service.mapper.MappingContext;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APINbaMappingContextConfiguration {
    @Bean(name = "nbaapiTeamMappingContext")
    public MappingContext<TeamData, APINbaTeamResponseDto> teamMappingContext() {
        MappingContext<TeamData, APINbaTeamResponseDto> context = new MappingContext<>(TeamData.class, APINbaTeamResponseDto.class);
        Converter<APINbaTeamResponseDto.LeagueWrapper, TeamData.League> leagueConverter = ctx -> {
            APINbaTeamResponseDto.League source = ctx.getSource().standard;
            return new TeamData.League(source.conference, source.division);
        };
        Converter<TeamData.League, APINbaTeamResponseDto.LeagueWrapper> leagueWrapperConverter = ctx -> {
            APINbaTeamResponseDto.LeagueWrapper leagueWrapper = new APINbaTeamResponseDto.LeagueWrapper();
            APINbaTeamResponseDto.League league = new APINbaTeamResponseDto.League();
            TeamData.League source = ctx.getSource();
            league.conference = source.conference();
            league.division = source.division();
            leagueWrapper.standard = league;
            return leagueWrapper;
        };
        return context.newBuilder()
                .useNonNull()
                .useStrict()
                .addFieldConverterOnEntity(leagueConverter, APINbaTeamResponseDto::getLeagues, TeamData::setLeague)
                .addFieldConverterOnDto(leagueWrapperConverter, TeamData::getLeague, APINbaTeamResponseDto::setLeagues)
                .addEntityMapping(APINbaTeamResponseDto::getId, TeamData::setExternalId)
                .addDtoMapping(TeamData::getExternalId, APINbaTeamResponseDto::setId)
                .addEntitySkips(TeamData::setId)
                .build();
    }
}
