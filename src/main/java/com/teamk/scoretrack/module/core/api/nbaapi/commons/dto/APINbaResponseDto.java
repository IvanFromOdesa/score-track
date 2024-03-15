package com.teamk.scoretrack.module.core.api.nbaapi.commons.dto;

import java.util.List;

public abstract class APINbaResponseDto<RESPONSE_TYPE> {
    public String get;
    public int results;
    public List<RESPONSE_TYPE> response;

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
}
