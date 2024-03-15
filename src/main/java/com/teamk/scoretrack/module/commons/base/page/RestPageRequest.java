package com.teamk.scoretrack.module.commons.base.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPageRequest extends PageRequest {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPageRequest(@JsonProperty("pageNumber") int page,
                           @JsonProperty("pageSize") int size,
                           @JsonProperty("sort") RestSort sort) {
        super(page, size, sort);
    }

    public RestPageRequest(Pageable pageable) {
        super(pageable.getPageNumber(), pageable.getPageSize(), RestSort.of(pageable.getSort()));
    }
}
