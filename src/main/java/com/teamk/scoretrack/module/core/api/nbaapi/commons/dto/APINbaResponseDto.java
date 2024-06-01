package com.teamk.scoretrack.module.core.api.nbaapi.commons.dto;

import java.util.List;
import java.util.Map;

public abstract class APINbaResponseDto<RESPONSE_TYPE> {
    protected String get;
    protected int results;
    protected List<RESPONSE_TYPE> response;
    /**
     * This is such a crappy API thing that I can't do anything about.
     * The api sends 'errors' as an empty array if there is no errors in the request,
     * otherwise it sends 'errors' as an JSON Object, which can be deserialized into a Map
     */
    protected Object errors;

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public List<RESPONSE_TYPE> getResponse() {
        return response;
    }

    public void setResponse(List<RESPONSE_TYPE> response) {
        this.response = response;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    /**
     * This is relatively safe - ONLY if the response is empty,
     * then errors is sent as JSON Object which can be safely cast
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getErrorsAsMap() {
        return (Map<String, String>) errors;
    }
}
