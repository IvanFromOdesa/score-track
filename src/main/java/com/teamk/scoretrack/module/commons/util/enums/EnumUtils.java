package com.teamk.scoretrack.module.commons.util.enums;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.teamk.scoretrack.module.commons.util.enums.convert.IEnumConvert;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public static <E extends Enum<E> & IEnumConvert<?, E>> E deserializeEnumValue(JsonNode node, E e) {
        JsonNode codeNode = getCodeNode(node);
        Integer code = codeNode != null ? codeNode.asInt() : null;
        return code != null ? e.getLookup().get(code) : null;
    }

    private static JsonNode getCodeNode(JsonNode node) {
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(node.fields(), Spliterator.ORDERED), false)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).get("code");
    }
}
