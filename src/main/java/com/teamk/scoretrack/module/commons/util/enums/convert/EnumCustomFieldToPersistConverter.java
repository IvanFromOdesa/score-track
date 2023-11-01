package com.teamk.scoretrack.module.commons.util.enums.convert;

import jakarta.persistence.AttributeConverter;

/**
 * @author Ivan Krylosov
 * @param <K> key type for this enum class' LOOKUP_MAP
 * @param <E> enum type
 * @param <TYPE> db type to convert this enum instances to
 */
public abstract class EnumCustomFieldToPersistConverter<K, E extends Enum<E> & IEnumConvert<K, E>, TYPE> implements AttributeConverter<E, TYPE> {
    protected E byKey(E e, K k) {
        return e.getByKey(k);
    }

    protected K byValue(E e) {
        return e.getKey();
    }
}
