package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.convert;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.*;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection.PlayerDataLeaderboardProjection;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerGamePositionDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerPositionDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayerStatsDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.NbaTeamInfoHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamExtendedDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamShortDto;
import org.modelmapper.Converter;

import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class PlayerDataEntityDtoConvertHelper {
    public static Converter<APINbaPlayerResponseDto.BirthData, PlayerData.PlayerBirth> getBirthConverter() {
        return ctx -> {
            APINbaPlayerResponseDto.BirthData birthData = ctx.getSource();
            return new PlayerData.PlayerBirth(birthData.date(), birthData.country());
        };
    }

    public static Converter<PlayerData.PlayerBirth, APINbaPlayerResponseDto.BirthData> getBirthWrapperConverter() {
        return ctx -> {
            PlayerData.PlayerBirth birth = ctx.getSource();
            return new APINbaPlayerResponseDto.BirthData(birth.date(), birth.country());
        };
    }

    public static Converter<APINbaPlayerResponseDto.CareerData, PlayerData.CareerData> getCareerConverter() {
        return ctx -> {
            APINbaPlayerResponseDto.CareerData careerData = ctx.getSource();
            return new PlayerData.CareerData(String.valueOf(careerData.start()), careerData.pro());
        };
    }

    public static Converter<PlayerData.CareerData, APINbaPlayerResponseDto.CareerData> getCareerWrapperConverter() {
        return ctx -> {
            PlayerData.CareerData careerData = ctx.getSource();
            return new APINbaPlayerResponseDto.CareerData(Integer.parseInt(careerData.start()), careerData.pro());
        };
    }

    public static Converter<APINbaPlayerResponseDto.HeightData, PlayerData.HeightData> getHeightConverter() {
        return ctx -> {
            APINbaPlayerResponseDto.HeightData heightData = ctx.getSource();
            // I don't like, but the API sends null for some player data height
            String feets = heightData.feets();
            String inches = heightData.inches();
            String meters = heightData.meters();
            return new PlayerData.HeightData(
                    feets != null ? Integer.parseInt(feets) : null,
                    inches != null ? Integer.parseInt(inches) : null,
                    meters != null ? Double.parseDouble(meters) : null
            );
        };
    }

    public static Converter<PlayerData.HeightData, APINbaPlayerResponseDto.HeightData> getHeightWrapperConverter() {
        return ctx -> {
            PlayerData.HeightData heightData = ctx.getSource();
            return new APINbaPlayerResponseDto.HeightData(String.valueOf(heightData.feets()), String.valueOf(heightData.inches()), String.valueOf(heightData.meters()));
        };
    }

    public static Converter<APINbaPlayerResponseDto.WeightData, PlayerData.WeightData> getWeightConverter() {
        return ctx -> {
            APINbaPlayerResponseDto.WeightData weightData = ctx.getSource();
            // Same thing here
            String pounds = weightData.pounds();
            String kilograms = weightData.kilograms();
            return new PlayerData.WeightData(
                    pounds != null ? Double.parseDouble(pounds) : null,
                    kilograms != null ? Double.parseDouble(kilograms) : null
            );
        };
    }

    public static Converter<PlayerData.WeightData, APINbaPlayerResponseDto.WeightData> getWeightWrapperConverter() {
        return ctx -> {
            PlayerData.WeightData weightData = ctx.getSource();
            return new APINbaPlayerResponseDto.WeightData(String.valueOf(weightData.pounds()), String.valueOf(weightData.kilograms()));
        };
    }

    public static Converter<Map<String, APINbaPlayerResponseDto.League>, Map<String, PlayerData.League>> getLeagueMapConverter() {
        return ctx -> {
            Map<String, APINbaPlayerResponseDto.League> source = ctx.getSource();
            return source.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> {
                APINbaPlayerResponseDto.League league = e.getValue();
                String value = league.pos() != null ? league.pos().getValue() : null;
                return new PlayerData.League(String.valueOf(league.jersey()), league.active(), PlayerPosition.byId(value));
            }));
        };
    }

    public static Converter<Map<String, PlayerData.League>, Map<String, APINbaPlayerResponseDto.League>> getLeagueWrapperConverter(UnaryOperator<String> uiTextCallback) {
        return ctx -> {
            Map<String, PlayerData.League> source = ctx.getSource();
            return source.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> {
                PlayerData.League league = e.getValue();
                String id = league.position().getId();
                return new APINbaPlayerResponseDto.League(Integer.parseInt(league.jersey()), league.active(), new APINbaPlayerPositionDto(id, uiTextCallback.apply(id)));
            }));
        };
    }

    public static Converter<APINbaPlayerStatsDto.GameId, PlayerStats.GameId> getPlayerStatsGameIdConverter() {
        return ctx -> {
            APINbaPlayerStatsDto.GameId source = ctx.getSource();
            return new PlayerStats.GameId(source.id());
        };
    }

    public static Converter<PlayerStats.GameId, APINbaPlayerStatsDto.GameId> getPlayerStatsGameIdWrapperConverter() {
        return ctx -> {
            PlayerStats.GameId source = ctx.getSource();
            return new APINbaPlayerStatsDto.GameId(source.id());
        };
    }

    public static Converter<APINbaPlayerGamePositionDto, PlayerGamePosition> getPlayerGamePositionConverter() {
        return ctx -> {
            APINbaPlayerGamePositionDto source = ctx.getSource();
            return PlayerGamePosition.byId(source != null ? source.getValue() : null);
        };
    }

    public static Converter<PlayerGamePosition, APINbaPlayerGamePositionDto> getPlayerGamePositionWrapperConverter(UnaryOperator<String> uiTextCallback) {
        return ctx -> {
            String id = ctx.getSource().getId();
            return new APINbaPlayerGamePositionDto(id, uiTextCallback.apply(id));
        };
    }

    public static Converter<APINbaPlayerStatsDto.TeamId, PlayerStats.TeamId> getPlayerStatsTeamIdConverter() {
        return ctx -> {
            APINbaPlayerStatsDto.TeamId source = ctx.getSource();
            return new PlayerStats.TeamId(String.valueOf(source.id()), source.name(), source.nickname(), source.code(), source.logo());
        };
    }

    public static Converter<PlayerStats.TeamId, APINbaPlayerStatsDto.TeamId> getPlayerStatsTeamIdWrapperConverter() {
        return ctx -> {
            PlayerStats.TeamId source = ctx.getSource();
            return new APINbaPlayerStatsDto.TeamId(Long.parseLong(source.id()), source.name(), source.nickname(), source.code(), source.logo());
        };
    }

    public static Converter<APINbaPlayerStatsDto.PlayerId, PlayerStats.PlayerId> getPlayerStatsPlayerIdConverter() {
        return ctx -> {
            APINbaPlayerStatsDto.PlayerId source = ctx.getSource();
            return new PlayerStats.PlayerId(String.valueOf(source.id()), source.firstname(), source.lastname());
        };
    }

    public static Converter<PlayerStats.PlayerId, APINbaPlayerStatsDto.PlayerId> getPlayerStatsPlayerIdWrapperConverter() {
        return ctx -> {
            PlayerStats.PlayerId source = ctx.getSource();
            return new APINbaPlayerStatsDto.PlayerId(Long.parseLong(source.id()), source.firstName(), source.lastName());
        };
    }

    public static Converter<PlayerDataLeaderboardProjection, APINbaTeamShortDto> getPlayerEfficiencyTeamShortWrapperConverter() {
        return ctx -> {
            PlayerDataLeaderboardProjection source = ctx.getSource();
            return new APINbaTeamShortDto(source.getTeamName(), source.getTeamLogo(), source.getTeamCode(), source.getTeamExternalId());
        };
    }

    public static Converter<String, String> getPlayerProfileImgWrapperConverter(Function<String, PlayerHeadshotImg> profileImgCallback) {
        return ctx -> {
            String id = ctx.getSource();
            PlayerHeadshotImg playerHeadshotImg = profileImgCallback.apply(id);
            return playerHeadshotImg != null ? playerHeadshotImg.imgUrl() : null;
        };
    }

    public static Converter<Map<SupportedSeasons, TeamData>, Map<String, APINbaTeamExtendedDto>> getPlayerTeamBySeasonWrapperConverter() {
        return ctx -> {
            Map<SupportedSeasons, TeamData> source = ctx.getSource();
            return source.entrySet().stream().collect(Collectors.toMap(
                    e -> String.valueOf(e.getKey().getYear()),
                    e -> {
                        TeamData value = e.getValue();
                        String code = value.getCode();
                        return new APINbaTeamExtendedDto(
                                value.getName(),
                                value.getLogo(),
                                code,
                                value.getExternalId(),
                                NbaTeamInfoHelper.getByCode(code));
                    }));
        };
    }

    public static Converter<String, Double> getPlayerStatsMinConverter() {
        return ctx -> {
            try {
                String[] parts = ctx.getSource().split(":");
                double totalMinutes;
                if (parts.length == 1) {
                    totalMinutes = Double.parseDouble(parts[0]);
                } else if (parts.length == 2) {
                    totalMinutes = Double.parseDouble(parts[0]) + (Double.parseDouble(parts[1]) / 60.0);
                } else {
                    return (double) 0;
                }
                return Math.round(totalMinutes * 100.0) / 100.0;
            } catch (NumberFormatException e) {
                return (double) 0;
            }
        };
    }
}
