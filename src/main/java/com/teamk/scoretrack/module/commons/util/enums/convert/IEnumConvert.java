package com.teamk.scoretrack.module.commons.util.enums.convert;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.BiMap;

public interface IEnumConvert<K, E extends Enum<E>> {
    BiMap<K, E> getLookup();

    default E getByKey(K key) {
        E e = getLookup().get(key);
        return e == null ? getDefault() : e;
    }

    E getDefault();

    @JsonValue
    K getKey();
}
