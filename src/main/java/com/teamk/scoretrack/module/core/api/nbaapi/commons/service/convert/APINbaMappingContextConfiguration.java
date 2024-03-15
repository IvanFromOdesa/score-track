package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.convert;

import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert.TeamDataEntityDtoConvertHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsDto;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class APINbaMappingContextConfiguration {
    @Bean(name = "nbaapiTeamMappingContext")
    public MappingContext<TeamData, APINbaTeamResponseDto> teamMappingContext() {
        MappingContext<TeamData, APINbaTeamResponseDto> context = new MappingContext<>(TeamData.class, APINbaTeamResponseDto.class);
        Converter<Map<String, APINbaTeamResponseDto.League>, Map<String, TeamData.League>> leagueConverter = ctx -> TeamDataEntityDtoConvertHelper.getLeagueMap(ctx.getSource());
        Converter<Map<String, TeamData.League>, Map<String, APINbaTeamResponseDto.League>> leagueWrapperConverter = ctx -> TeamDataEntityDtoConvertHelper.getLeagueWrapper(ctx.getSource());
        return context.newBuilder()
                .useNonNull()
                .useStrict()
                .addFieldConverterOnEntity(leagueConverter, APINbaTeamResponseDto::getLeagues, TeamData::setLeagues)
                .addFieldConverterOnDto(leagueWrapperConverter, TeamData::getLeagues, APINbaTeamResponseDto::setLeagues)
                .addEntityMapping(APINbaTeamResponseDto::getId, TeamData::setExternalId)
                .addDtoMapping(TeamData::getExternalId, APINbaTeamResponseDto::setId)
                .addEntitySkips(TeamData::setId)
                .build();
    }

    @Bean(name = "nbaapiTeamStatsMappingContext")
    public MappingContext<TeamStats, APINbaTeamStatsDto> teamStatsMappingContext() {
        MappingContext<TeamStats, APINbaTeamStatsDto> context = new MappingContext<>(TeamStats.class, APINbaTeamStatsDto.class);
        Converter<String, Double> srtConverter = ctx -> Double.parseDouble(ctx.getSource());
        Converter<Double, String> dbConverter = ctx -> String.valueOf(ctx.getSource());
        return context.newBuilder()
                .useNonNull()
                .useStrict()
                .addFieldConverterOnEntity(srtConverter, APINbaTeamStatsDto::getFgp, TeamStats::setFgp)
                .addFieldConverterOnEntity(srtConverter, APINbaTeamStatsDto::getFtp, TeamStats::setFtp)
                .addFieldConverterOnEntity(srtConverter, APINbaTeamStatsDto::getTpp, TeamStats::setTpp)
                .addFieldConverterOnDto(dbConverter, TeamStats::getFgp, APINbaTeamStatsDto::setFgp)
                .addFieldConverterOnDto(dbConverter, TeamStats::getFtp, APINbaTeamStatsDto::setFtp)
                .addFieldConverterOnDto(dbConverter, TeamStats::getTpp, APINbaTeamStatsDto::setTpp)
                .build();
    }
}
