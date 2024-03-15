package com.teamk.scoretrack.module.commons.mongo.service;

import com.teamk.scoretrack.module.commons.exception.BaseErrorMapException;
import com.teamk.scoretrack.module.commons.mongo.domain.Identifier;
import com.teamk.scoretrack.module.commons.base.service.mapper.DtoEntityConvertService;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.commons.other.ErrorMapBeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

/**
 * Services that expose DTOs to caller.
 */
public abstract class AbstractMongoDtoService<ENTITY extends Identifier, DTO> {
    protected AbstractMongoEntityService<ENTITY, ?> entityService;
    protected DtoEntityConvertService<ENTITY, DTO> convertService;
    @Autowired
    protected ErrorMapBeanWrapper errorMapBeanWrapper;

    public Page<DTO> getDtoPage(int page, int size) {
        validate(page, size);
        return getEntityPage(page, size).map(e -> convertService.toDto(e));
    }

    public Page<DTO> getDtoPage(int page, int size, String... sortBys) {
        validate(page, size);
        return getEntityPage(page, size, sortBys).map(e -> convertService.toDto(e));
    }

    protected Page<ENTITY> getEntityPage(int page, int size) {
        return entityService.getAll(page, size);
    }

    protected Page<ENTITY> getEntityPage(int page, int size, String[] sortBys) {
        return entityService.getAll(page, size, "asc", sortBys);
    }

    private void validate(int page, int size) {
        ErrorMap errorMap = errorMapBeanWrapper.getErrorMap();
        if (page < 0) {
            errorMap.put("page", "Page can't be less than 0");
        }
        if (size <= 0) {
            errorMap.put("size", "Size must be larger than 0");
        }
        if (!errorMap.isEmpty()) {
            throw new BaseErrorMapException(errorMap);
        }
    }

    protected abstract void setEntityService(AbstractMongoEntityService<ENTITY, ?> entityService);
    protected abstract void setConvertService(DtoEntityConvertService<ENTITY, DTO> convertService);
}
