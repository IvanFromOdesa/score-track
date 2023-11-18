package com.teamk.scoretrack.module.commons.mongo.service.mapper;

import com.teamk.scoretrack.module.commons.exception.BaseErrorMapException;
import com.teamk.scoretrack.module.commons.mongo.domain.Identifier;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.commons.base.service.valid.form.DtoEntityValidator;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.Provider;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.SourceGetter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class DtoEntityConvertService<ENTITY extends Identifier, DTO> {
    protected final MappingContext<ENTITY, DTO> mappingContext;
    protected final DtoEntityValidator<DTO, FormValidationContext<DTO>> validator;
    protected final Logger LOGGER;

    @Autowired
    public DtoEntityConvertService(MappingContext<ENTITY, DTO> mappingContext, DtoEntityValidator<DTO, FormValidationContext<DTO>> validator) {
        this.mappingContext = mappingContext;
        this.validator = validator;
        LOGGER = resolveLogger();
    }

    protected abstract Logger resolveLogger();

    protected void resolveErrorResponse(ErrorMap errorMap) {
        throw new BaseErrorMapException(errorMap);
    }

    protected Optional<ENTITY> withValidate(DTO dto, Supplier<ENTITY> mapper) {
        ErrorMap map = validator.validate(new FormValidationContext<>(dto));
        if (map.isEmpty()) {
            return Optional.ofNullable(mapper.get());
        } else {
            resolveErrorResponse(map);
        }
        return Optional.empty();
    }

    public Optional<ENTITY> toEntity(DTO dto) {
        return withValidate(dto, () -> mappingContext.getDtoToEntity().convert(dto));
    }

    public Optional<ENTITY> toEntity(DTO dto, Provider<ENTITY> provider) {
        return withValidate(dto, () -> mappingContext.getDtoToEntity().convert(dto, provider));
    }

    public <ARG_TO_TYPE> Optional<ENTITY> toEntity(DTO dto, DestinationSetter<ENTITY, ARG_TO_TYPE> toSkip) {
        return withValidate(dto, () -> mappingContext.getDtoToEntity().convert(dto, toSkip));
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> Optional<ENTITY> toEntity(DTO dto, Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter, SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TO_TYPE> setter) {
        return withValidate(dto, () -> mappingContext.getDtoToEntity().convert(dto, converter, getter, setter));
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> Optional<ENTITY> toEntity(DTO dto, Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<DTO> getter, DestinationSetter<ENTITY, ARG_TO_TYPE> setter) {
        return withValidate(dto, () -> mappingContext.getDtoToEntity().convert(dto, condition, getter, setter));
    }

    public DTO toDto(ENTITY entity) {
        return mappingContext.getEntityToDto().convert(entity);
    }

    public DTO toDto(ENTITY entity, Provider<DTO> provider) {
        return mappingContext.getEntityToDto().convert(entity, provider);
    }

    public <ARG_TO_TYPE> DTO toDto(ENTITY entity, DestinationSetter<DTO, ARG_TO_TYPE> toSkip) {
        return mappingContext.getEntityToDto().convert(entity, toSkip);
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> DTO toDto(ENTITY entity, Converter<ARG_FROM_TYPE, ARG_TO_TYPE> converter, SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TO_TYPE> setter) {
        return mappingContext.getEntityToDto().convert(entity, converter, getter, setter);
    }

    public <ARG_FROM_TYPE, ARG_TO_TYPE> DTO toDto(ENTITY entity, Condition<ARG_FROM_TYPE, ARG_TO_TYPE> condition, SourceGetter<ENTITY> getter, DestinationSetter<DTO, ARG_TO_TYPE> setter) {
        return mappingContext.getEntityToDto().convert(entity, condition, getter, setter);
    }
}
