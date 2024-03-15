package com.teamk.scoretrack.module.commons.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public final class HibernateRelations {

    public static <T, C extends Collection<T>> void updateManyToOne(C updateCollection, C domainCollection) {
        updateManyToOne(updateCollection, domainCollection, Function.identity());
    }

    public static <T, C extends Collection<T>> void updateManyToOne(Supplier<C> updateCollectionSupplier, C domainCollection) {
        updateManyToOne(updateCollectionSupplier.get(), domainCollection, Function.identity());
    }

    public static<T, U, C extends Collection<T>, D extends Collection<U>> void updateManyToOne(C updateCollection, D domainCollection, Function<C, D> transformer) {
        if (updateCollection == null || updateCollection.size() == 0) {
            domainCollection.clear();
        } else {
            D transformed = transformer.apply(updateCollection);
            domainCollection.removeIf(u -> !transformed.contains(u));
            domainCollection.addAll(transformed);
        }
    }
}
