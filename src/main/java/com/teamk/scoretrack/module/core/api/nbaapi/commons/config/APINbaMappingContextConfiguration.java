package com.teamk.scoretrack.module.core.api.nbaapi.commons.config;

import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentMetadata;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdateMetadata;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.Stats;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.StatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerLeaderboardDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.convert.TeamDataEntityDtoConvertHelper;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.teamk.scoretrack.module.commons.util.mapper.CommonConverters.intToString;
import static com.teamk.scoretrack.module.commons.util.mapper.CommonConverters.stringToInt;
import static com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert.PlayerDataEntityDtoConvertHelper.*;

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
        return getDefaultStatsBuilder(new MappingContext<>(TeamStats.class, APINbaTeamStatsDto.class)).build();
    }

    @Bean(name = "nbaapiPlayerMappingContext")
    public MappingContext<PlayerData, APINbaPlayerResponseDto> playerMappingContext() {
        MappingContext<PlayerData, APINbaPlayerResponseDto> context = new MappingContext<>(PlayerData.class, APINbaPlayerResponseDto.class);
        return context.newBuilder()
                .useNonNull()
                .useStrict()
                .addFieldConverterOnEntity(getBirthConverter(), APINbaPlayerResponseDto::getBirth, PlayerData::setBirth)
                .addFieldConverterOnDto(getBirthWrapperConverter(), PlayerData::getBirth, APINbaPlayerResponseDto::setBirth)
                .addFieldConverterOnEntity(getCareerConverter(), APINbaPlayerResponseDto::getNba, PlayerData::setNba)
                .addFieldConverterOnDto(getCareerWrapperConverter(), PlayerData::getNba, APINbaPlayerResponseDto::setNba)
                .addFieldConverterOnEntity(getHeightConverter(), APINbaPlayerResponseDto::getHeight, PlayerData::setHeight)
                .addFieldConverterOnDto(getHeightWrapperConverter(), PlayerData::getHeight, APINbaPlayerResponseDto::setHeight)
                .addFieldConverterOnEntity(getWeightConverter(), APINbaPlayerResponseDto::getWeight, PlayerData::setWeight)
                .addFieldConverterOnDto(getWeightWrapperConverter(), PlayerData::getWeight, APINbaPlayerResponseDto::setWeight)
                .addFieldConverterOnEntity(getLeagueMapConverter(), APINbaPlayerResponseDto::getLeagues, PlayerData::setLeagues)
                .addFieldConverterOnDto(getLeagueWrapperConverter(), PlayerData::getLeagues, APINbaPlayerResponseDto::setLeagues)
                .addEntityMapping(APINbaPlayerResponseDto::getFirstname, PlayerData::setFirstName)
                .addDtoMapping(PlayerData::getFirstName, APINbaPlayerResponseDto::setFirstname)
                .addEntityMapping(APINbaPlayerResponseDto::getLastname, PlayerData::setLastName)
                .addDtoMapping(PlayerData::getLastName, APINbaPlayerResponseDto::setLastname)
                .addEntityMapping(APINbaPlayerResponseDto::getId, PlayerData::setExternalId)
                .addDtoMapping(PlayerData::getExternalId, APINbaPlayerResponseDto::setId)
                .addEntitySkips(PlayerData::setId)
                .build();
    }

    @Bean(name = "nbaapiPlayerStatsMappingContext")
    public MappingContext<PlayerStats, APINbaPlayerStatsDto> playerStatsMappingContext() {
        MappingContext<PlayerStats, APINbaPlayerStatsDto> context = new MappingContext<>(PlayerStats.class, APINbaPlayerStatsDto.class);
        return getDefaultStatsBuilder(context)
                .addFieldConverterOnEntity(getPlayerStatsGameIdConverter(), APINbaPlayerStatsDto::getGame, PlayerStats::setGame)
                .addFieldConverterOnDto(getPlayerStatsGameIdWrapperConverter(), PlayerStats::getGame, APINbaPlayerStatsDto::setGame)
                .addFieldConverterOnEntity(getPlayerGamePositionConverter(), APINbaPlayerStatsDto::getPos, PlayerStats::setPosition)
                .addFieldConverterOnDto(getPlayerGamePositionWrapperConverter(), PlayerStats::getPosition, APINbaPlayerStatsDto::setPos)
                .addFieldConverterOnEntity(getPlayerStatsTeamIdConverter(), APINbaPlayerStatsDto::getTeam, PlayerStats::setTeam)
                .addFieldConverterOnDto(getPlayerStatsTeamIdWrapperConverter(), PlayerStats::getTeam, APINbaPlayerStatsDto::setTeam)
                .addFieldConverterOnEntity(getPlayerStatsPlayerIdConverter(), APINbaPlayerStatsDto::getPlayer, PlayerStats::setPlayer)
                .addFieldConverterOnDto(getPlayerStatsPlayerIdWrapperConverter(), PlayerStats::getPlayer, APINbaPlayerStatsDto::setPlayer)
                // FIXME: String to Integer converter
                .addFieldConverterOnEntity(stringToInt(), APINbaPlayerStatsDto::getMin, PlayerStats::setMinutes)
                .addFieldConverterOnDto(intToString(), PlayerStats::getMinutes, APINbaPlayerStatsDto::setMin)
                .addFieldConverterOnEntity(stringToInt(), APINbaPlayerStatsDto::getPlusMinus, PlayerStats::setPlusMinus)
                .addFieldConverterOnDto(intToString(), PlayerStats::getPlusMinus, APINbaPlayerStatsDto::setPlusMinus)
                .build();
    }

    @Bean(name = "nbaapiPlayerEfficiencyMappingContext")
    public MappingContext<PlayerDataLeaderboardProjection, APINbaPlayerLeaderboardDto> playerEfficiencyMappingContext() {
        return new MappingContext<>(PlayerDataLeaderboardProjection.class, APINbaPlayerLeaderboardDto.class)
                .newBuilder()
                .useNonNull()
                .useStrict()
                .addFieldConverterOnDto(getPlayerEfficiencyTeamShortWrapperConverter(), e -> e, APINbaPlayerLeaderboardDto::setTeam)
                .build();
    }

    private static<ENTITY extends Stats, DTO extends StatsDto<?>> MappingContext<ENTITY, DTO>.Builder getDefaultStatsBuilder(MappingContext<ENTITY, DTO> context) {
        Converter<String, Double> srtConverter = ctx -> Double.parseDouble(ctx.getSource());
        Converter<Double, String> dbConverter = ctx -> String.valueOf(ctx.getSource());
        return context.newBuilder()
                .useNonNull()
                .useStrict()
                .addFieldConverterOnEntity(srtConverter, StatsDto::getFgp, Stats::setFgp)
                .addFieldConverterOnEntity(srtConverter, StatsDto::getFtp, Stats::setFtp)
                .addFieldConverterOnEntity(srtConverter, StatsDto::getTpp, Stats::setTpp)
                .addFieldConverterOnDto(dbConverter, Stats::getFgp, StatsDto::setFgp)
                .addFieldConverterOnDto(dbConverter, Stats::getFtp, StatsDto::setFtp)
                .addFieldConverterOnDto(dbConverter, Stats::getTpp, StatsDto::setTpp);
    }

    @Bean(name = "nbaapiComponentMetadataMappingContext")
    public MappingContext<APINbaUpdateMetadata, SportComponentMetadata> componentMetadataMappingContext() {
        MappingContext<APINbaUpdateMetadata, SportComponentMetadata> context = new MappingContext<>(APINbaUpdateMetadata.class, SportComponentMetadata.class);
        Converter<APINbaUpdate.Status, SportComponentMetadata.Status> converter = ctx -> ctx.getSource().isFinished() ? SportComponentMetadata.Status.ACCESSIBLE : SportComponentMetadata.Status.DOWN;
        return context.newBuilder()
                .useNonNull()
                .useStrict()
                .addDtoMapping(APINbaUpdateMetadata::getUpdated, SportComponentMetadata::setUpdated)
                .addDtoMapping(APINbaUpdateMetadata::getName, SportComponentMetadata::setName)
                .addDtoMapping(APINbaUpdateMetadata::getUpdateCount, SportComponentMetadata::setUpdateCount)
                .addFieldConverterOnDto(converter, APINbaUpdateMetadata::getStatus, SportComponentMetadata::setStatus)
                .build();
    }
}
