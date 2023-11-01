package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

import java.util.List;

public class APINbaTeamsResponseDto {
    public String get;
    public int results;
    public List<APINbaTeamResponseDto> response;

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

    public List<APINbaTeamResponseDto> getResponse() {
        return response;
    }

    public void setResponse(List<APINbaTeamResponseDto> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "APINbaTeamsResponseDto{" +
                "get='" + get + '\'' +
                ", results=" + results +
                ", response=" + response.toString() +
                '}';
    }
}
