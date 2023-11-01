package com.teamk.scoretrack.module.commons.service;

import com.teamk.scoretrack.module.commons.domain.IdAware;
import com.teamk.scoretrack.module.commons.exception.BaseEntityNotFoundException;
import com.teamk.scoretrack.module.commons.exception.BaseErrorMapException;
import com.teamk.scoretrack.module.commons.other.ErrorMapBeanWrapper;
import com.teamk.scoretrack.module.commons.other.ScoreTrackConfig;
import com.teamk.scoretrack.module.commons.util.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractEntityService<ENTITY extends IdAware<ID>, ID,
        DAO extends ListCrudRepository<ENTITY, ID>
                & ListPagingAndSortingRepository<ENTITY, ID>
                & QueryByExampleExecutor<ENTITY>> {
    protected DAO dao;
    @Autowired
    protected BaseTransactionManager baseTransactionManager;
    @Autowired
    protected ErrorMapBeanWrapper errorMapBeanWrapper;
    protected final Logger LOGGER;

    protected AbstractEntityService() {
        this.LOGGER = LoggerFactory.getLogger(this.getClass());
    }

    public ID save(ENTITY e) {
        return dao.save(e).getId();
    }

    public ID update(ID id, ENTITY e) {
        return update(id, e, p -> dao.findById(p));
    }

    public <PROPERTY> ID update(PROPERTY p, ENTITY e, Function<PROPERTY, Optional<ENTITY>> findByCallback) {
        Optional<ENTITY> by = findByCallback.apply(p);
        if (by.isPresent()) {
            return save(merge(e, by.get()));
        }
        LOGGER.warn(String.format("Entity not found by property: %s.", p));
        return save(e);
    }

    /**
     * Standard merging mechanism
     * @param e
     * @param byId
     * @return
     */
    protected ENTITY merge(ENTITY e, ENTITY byId) {
        return BaseMapper.merge(e, byId);
    }

    public void delete(ID id) {
        Optional<ENTITY> byId = dao.findById(id);
        if (byId.isPresent()) {
            dao.delete(byId.get());
        } else {
            throw new BaseEntityNotFoundException(String.format("Entity not found by id: %s.", id), "id", ScoreTrackConfig.IS_REQUEST_SCOPE);
        }
    }

    /**
     * Transactional delete operation. Might be overriden for custom needs.
     * @param ids
     */
    public void deleteAll(List<ID> ids) {
        for (ID id : ids) {
            try {
                baseTransactionManager.doInNewTransaction(() -> delete(id));
            } catch (BaseEntityNotFoundException e) {
                if (e.isRequest()) {
                    errorMapBeanWrapper.getErrorMap().put(e.getStrCause(), e.getMessage());
                } else {
                   LOGGER.warn(e.getMessage());
                }
            }
        }
        if (!errorMapBeanWrapper.getErrorMap().isEmpty()) {
            throw new BaseErrorMapException(errorMapBeanWrapper.getErrorMap());
        }
    }

    public ENTITY getById(ID id) {
        Optional<ENTITY> byId = dao.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new BaseEntityNotFoundException(String.format("Entity not found by id: %s.", id), "id", ScoreTrackConfig.IS_REQUEST_SCOPE);
    }

    public Page<ENTITY> getAll(int page, int size) {
        return dao.findAll(PageRequest.of(page, size));
    }

    public Page<ENTITY> getAll(int page, int size, String direction, String... sortBys) {
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Direction dir = Sort.Direction.fromString(direction);
        for (String sortBy : sortBys) {
            orders.add(new Sort.Order(dir, sortBy));
        }
        return dao.findAll(PageRequest.of(page, size, Sort.by(orders)));
    }

    /*public abstract Class<ENTITY> getDomainClass();*/
    protected abstract void setDao(DAO dao);
}
