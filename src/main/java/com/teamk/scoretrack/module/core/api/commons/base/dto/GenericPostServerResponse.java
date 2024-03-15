package com.teamk.scoretrack.module.core.api.commons.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamk.scoretrack.module.commons.other.ErrorMap;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericPostServerResponse<T> {
    private T data;
    private Map<String, ErrorMap.Error> errors;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, ErrorMap.Error> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, ErrorMap.Error> errors) {
        this.errors = errors;
    }
}
