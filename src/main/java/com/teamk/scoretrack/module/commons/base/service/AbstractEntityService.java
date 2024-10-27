package com.teamk.scoretrack.module.commons.base.service;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;
import com.teamk.scoretrack.module.commons.exception.BaseEntityNotFoundException;
import com.teamk.scoretrack.module.commons.exception.BaseErrorMapException;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.commons.other.ErrorMapBeanWrapper;
import com.teamk.scoretrack.module.commons.other.ScoreTrackConfig;
import com.teamk.scoretrack.module.commons.util.mapper.BaseMapper;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractEntityService<ENTITY extends IdAware<ID>, ID,
        DAO extends ListCrudRepository<ENTITY, ID>
                & ListPagingAndSortingRepository<ENTITY, ID>
                & QueryByExampleExecutor<ENTITY>> {
    protected DAO dao;
    @Autowired
    protected BaseTransactionManager baseTransactionManager;
    @Autowired
    protected EntityManager entityManager;
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
        return update(e, () -> dao.findById(id));
    }

    public ID update(ENTITY e, Supplier<Optional<ENTITY>> findByCallback) {
        Optional<ENTITY> by = findByCallback.get();
        if (by.isPresent()) {
            return save(merge(e, by.get()));
        }
        LOGGER.warn("Entity not found by callback.");
        return save(e);
    }

    public void updateAll(Collection<ENTITY> entities) {
        updateAll(entities, e -> getById(e.getId()), ArrayList::new);
    }

    protected void updateAll(Collection<ENTITY> entities, Function<ENTITY, Optional<ENTITY>> findByCallback) {
        updateAll(entities, findByCallback, ArrayList::new);
    }

    protected <C extends Collection<ENTITY>> void updateAll(Collection<ENTITY> entities, Supplier<C> collectionSupplier) {
        updateAll(entities, e -> getById(e.getId()), collectionSupplier);
    }

    /**
     * @apiNote WARNING: This will retrieve all entities from the database that have the same IDs
     * as those in the provided collection, allowing for merging to be performed. Use carefully.
     * @param entities collection of entities to be saved at once
     * @param findByCallback callback function that takes entity from collection and returns an optional of existing db entity
     * @param collectionSupplier a supplier that provides a transform collection
     */
    protected <C extends Collection<ENTITY>> void updateAll(Collection<ENTITY> entities, Function<ENTITY, Optional<ENTITY>> findByCallback, Supplier<C> collectionSupplier) {
        dao.saveAll(entities.stream().map(e -> findByCallback.apply(e).map(en -> merge(e, en)).orElse(e)).collect(Collectors.toCollection(collectionSupplier)));
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
    public void deleteAll(Collection<ID> ids) {
        ErrorMap errorMap = errorMapBeanWrapper.getErrorMap();
        for (ID id : ids) {
            try {
                baseTransactionManager.doInNewTransaction(() -> delete(id));
            } catch (BaseEntityNotFoundException e) {
                if (e.isRequest()) {
                    errorMap.put(e.getStrCause(), e.getMessage());
                } else {
                   LOGGER.warn(e.getMessage());
                }
            }
        }
        if (!errorMap.isEmpty()) {
            throw new BaseErrorMapException(errorMap);
        }
    }

    public Optional<ENTITY> getById(ID id) {
        return dao.findById(id);
    }

    public ENTITY getByIdOrThrow(ID id) {
        Optional<ENTITY> byId = dao.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new BaseEntityNotFoundException(String.format("Entity not found by id: %s.", id), "id", ScoreTrackConfig.IS_REQUEST_SCOPE);
    }

    public Page<ENTITY> getAll(int page, int size) {
        return dao.findAll(getPageable(page, size));
    }

    public Page<ENTITY> getAll(int page, int size, String direction, String... sortBys) {
        return dao.findAll(getPageable(page, size, direction, sortBys));
    }

    public Page<ENTITY> getAll(int page, int size, Example<ENTITY> entityExample, String direction, String... sortBys) {
        return dao.findAll(entityExample, getPageable(page, size, direction, sortBys));
    }

    protected static PageRequest getPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    protected static PageRequest getPageable(int page, int size, String direction, String[] sortBys) {
        return PageRequest.of(page, size, Sort.by(getOrders(direction, sortBys)));
    }

    private static List<Sort.Order> getOrders(String direction, String[] sortBys) {
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Direction dir = Sort.Direction.fromString(direction);
        for (String sortBy : sortBys) {
            orders.add(new Sort.Order(dir, sortBy));
        }
        return orders;
    }

    /*public abstract Class<ENTITY> getDomainClass();*/
    protected abstract void setDao(DAO dao);
}
