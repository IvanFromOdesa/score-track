package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.config;

import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.config.APINbaResourceConfiguration;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerHeadshotImg;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerAvgStats;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerAvgStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerLeaderboardDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.i18n.APINbaPlayerTranslatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.teamk.scoretrack.module.commons.util.mapper.CommonConverters.intToString;
import static com.teamk.scoretrack.module.commons.util.mapper.CommonConverters.stringToInt;
import static com.teamk.scoretrack.module.core.api.nbaapi.commons.service.convert.StatsEntityDtoConvertHelper.getDefaultStatsBuilder;
import static com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert.PlayerDataEntityDtoConvertHelper.*;

@Configuration
public class APINbaPlayerMappingContextConfiguration {
    @Bean(name = "nbaapiPlayerMappingContext")
    public MappingContext<PlayerData, APINbaPlayerResponseDto> playerMappingContext(APINbaPlayerTranslatorService translatorService,
                                                                                    @Qualifier(APINbaResourceConfiguration.PLAYERS_IMGS) Map<String, PlayerHeadshotImg> playerProfileImgs) {
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
                .addFieldConverterOnDto(getLeagueWrapperConverter(translatorService::getMessage), PlayerData::getLeagues, APINbaPlayerResponseDto::setLeagues)
                .addFieldConverterOnDto(getPlayerProfileImgWrapperConverter(playerProfileImgs::get), PlayerData::getExternalId, APINbaPlayerResponseDto::setImgUrl)
                .addFieldConverterOnDto(getPlayerTeamBySeasonWrapperConverter(), PlayerData::getTeamBySeason, APINbaPlayerResponseDto::setTeamBySeason)
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
    public MappingContext<PlayerStats, APINbaPlayerStatsDto> playerStatsMappingContext(APINbaPlayerTranslatorService translatorService) {
        MappingContext<PlayerStats, APINbaPlayerStatsDto> context = new MappingContext<>(PlayerStats.class, APINbaPlayerStatsDto.class);
        return getDefaultStatsBuilder(context)
                .addFieldConverterOnEntity(getPlayerStatsGameIdConverter(), APINbaPlayerStatsDto::getGame, PlayerStats::setGame)
                .addFieldConverterOnDto(getPlayerStatsGameIdWrapperConverter(), PlayerStats::getGame, APINbaPlayerStatsDto::setGame)
                .addFieldConverterOnEntity(getPlayerGamePositionConverter(), APINbaPlayerStatsDto::getPos, PlayerStats::setPosition)
                .addFieldConverterOnDto(getPlayerGamePositionWrapperConverter(translatorService::getMessage), PlayerStats::getPosition, APINbaPlayerStatsDto::setPos)
                .addFieldConverterOnEntity(getPlayerStatsTeamIdConverter(), APINbaPlayerStatsDto::getTeam, PlayerStats::setTeam)
                .addFieldConverterOnDto(getPlayerStatsTeamIdWrapperConverter(), PlayerStats::getTeam, APINbaPlayerStatsDto::setTeam)
                .addFieldConverterOnEntity(getPlayerStatsPlayerIdConverter(), APINbaPlayerStatsDto::getPlayer, PlayerStats::setPlayer)
                .addFieldConverterOnDto(getPlayerStatsPlayerIdWrapperConverter(), PlayerStats::getPlayer, APINbaPlayerStatsDto::setPlayer)
                .addFieldConverterOnEntity(getPlayerStatsMinConverter(), APINbaPlayerStatsDto::getMin, PlayerStats::setMinutes)
                .addFieldConverterOnDto(intToString(), PlayerStats::getMinutes, APINbaPlayerStatsDto::setMin)
                .addFieldConverterOnEntity(stringToInt(), APINbaPlayerStatsDto::getPlusMinus, PlayerStats::setPlusMinus)
                .addFieldConverterOnDto(intToString(), PlayerStats::getPlusMinus, APINbaPlayerStatsDto::setPlusMinus)
                .build();
    }

    @Bean(name = "nbaapiPlayerDataLeaderboardMappingContext")
    public MappingContext<PlayerDataLeaderboardProjection, APINbaPlayerLeaderboardDto> playerDataLeaderboardMappingContext(@Qualifier(APINbaResourceConfiguration.PLAYERS_IMGS) Map<String, PlayerHeadshotImg> playerProfileImgs) {
        return new MappingContext<>(PlayerDataLeaderboardProjection.class, APINbaPlayerLeaderboardDto.class)
                .newBuilder()
                .useNonNull()
                .useStrict()
                .addFieldConverterOnDto(getPlayerEfficiencyTeamShortWrapperConverter(), e -> e, APINbaPlayerLeaderboardDto::setTeam)
                .addFieldConverterOnDto(getPlayerProfileImgWrapperConverter(playerProfileImgs::get), PlayerDataLeaderboardProjection::getId, APINbaPlayerLeaderboardDto::setImgUrl)
                .build();
    }

    @Bean(name = "nbaapiPlayerDataAvgStatsMappingContext")
    public MappingContext<PlayerAvgStats, APINbaPlayerAvgStatsDto> playerDataAvgStatsMappingContext(APINbaPlayerTranslatorService translatorService) {
        return new MappingContext<>(PlayerAvgStats.class, APINbaPlayerAvgStatsDto.class)
                .newBuilder()
                .useNonNull()
                .useStrict()
                .addFieldConverterOnDto(getPlayerGamePositionWrapperConverter(translatorService::getMessage), PlayerAvgStats::getPosition, APINbaPlayerAvgStatsDto::setPosition)
                .build();
    }
}
