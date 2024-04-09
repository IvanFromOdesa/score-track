package com.teamk.scoretrack.module.commons.mongo.dao;

import java.util.Collection;
import java.util.Optional;

/**
 * Provides operations to use via {@link org.springframework.data.mongodb.core.MongoTemplate}.
 * @param <P> projection type
 * @param <O> options type
 * @apiNote when using to combine MongoRepository with MongoTemplate, beware of dao naming.
 * The repo should begin with the same name as MongoRepository dao and should end with 'Impl'.
 */
public interface MongoProjection<P, O> {
    Optional<P> fetch(O options);

    Optional<P> fetch();

    Collection<P> fetchCollection(O options);

    Collection<P> fetchCollection();
}
