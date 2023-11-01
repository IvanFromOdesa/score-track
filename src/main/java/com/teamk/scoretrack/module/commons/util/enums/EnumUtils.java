package com.teamk.scoretrack.module.commons.util.enums;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public final class EnumUtils {
    /**
     * Creates a lookup map for the specified enum class. Requires enum to extend {@link IEnumConvert}.
     * @param enumTypeClass enum class
     * @return lookup map with key type provided on {@link IEnumConvert} and enum instance as value.
     * @param <T> enum type
     * @param <U> key type for the map
     */
    public static <T extends Enum<T> & IEnumConvert<U, T>, U> BiMap<U, T> createLookup(Class<T> enumTypeClass) {
        Map<U, T> lookup = new HashMap<>();
        for (T type : enumTypeClass.getEnumConstants()) {
            lookup.put(type.getKey(), type);
        }
        return ImmutableBiMap.copyOf(lookup);
    }

    public static <T extends Enum<T>> boolean isInSet(EnumSet<T> set, T target) {
        return set.contains(target);
    }
}
