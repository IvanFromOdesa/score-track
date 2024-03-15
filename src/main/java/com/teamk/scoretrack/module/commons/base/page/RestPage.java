package com.teamk.scoretrack.module.commons.base.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

/**
 * PageImpl that works with Redis deserialization
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPage<T> extends PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPage(@JsonProperty("content") List<T> content,
                    @JsonProperty("pageable") RestPageRequest pageable,
                    @JsonProperty("totalElements") long total) {
        super(content, pageable, total);
    }

    public RestPage(Page<T> page) {
        super(page.getContent(), new RestPageRequest(page.getPageable()), page.getTotalElements());
    }

    public static <T> RestPage<T> of(Page<T> page) {
        return new RestPage<>(page);
    }
}
