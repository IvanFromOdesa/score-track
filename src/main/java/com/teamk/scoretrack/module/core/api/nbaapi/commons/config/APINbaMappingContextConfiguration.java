package com.teamk.scoretrack.module.core.api.nbaapi.commons.config;

import com.teamk.scoretrack.module.commons.base.service.mapper.MappingContext;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentMetadata;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdateMetadata;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APINbaMappingContextConfiguration {
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
