package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.convert;

import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.Stats;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.StatsDto;
import org.modelmapper.Converter;

public class StatsEntityDtoConvertHelper {
    public static<ENTITY extends Stats, DTO extends StatsDto<?>> MappingContext<ENTITY, DTO>.Builder getDefaultStatsBuilder(MappingContext<ENTITY, DTO> context) {
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
}
