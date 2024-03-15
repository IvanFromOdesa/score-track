package com.teamk.scoretrack.module.commons.base.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Dumb ways to code unnecessary stuff (Sort does really only serialize {@link Sort#empty()}, {@link Sort#isSorted()}, {@link Sort#isUnsorted()})
 */
public class RestSort extends Sort {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestSort(@JsonProperty("empty") boolean empty,
                    @JsonProperty("sorted") boolean sorted,
                    @JsonProperty("unsorted") boolean unsorted) {
        // Based on Sort methods impl
        super(empty ? new ArrayList<>() : dummyOrders());
    }

    protected RestSort(List<Order> orders) {
        super(orders);
    }

    private static List<Order> dummyOrders() {
        return List.of(new Order(DEFAULT_DIRECTION, "_DUMMY"));
    }

    public static RestSort of(Sort sort) {
        return new RestSort(sort.toList());
    }
}
