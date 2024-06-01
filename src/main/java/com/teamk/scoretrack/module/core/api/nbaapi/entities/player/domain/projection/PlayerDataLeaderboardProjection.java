package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.projection;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;

public class PlayerDataLeaderboardProjection implements IdAware<String> {
    private String valueAvg;
    private String id;
    private String firstName;
    private String lastName;
    private String teamName;
    private String teamLogo;
    private String teamExternalId;
    private String teamCode;
    private String rank;

    public String getValueAvg() {
        return valueAvg;
    }

    public void setValueAvg(String valueAvg) {
        this.valueAvg = valueAvg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public String getTeamExternalId() {
        return teamExternalId;
    }

    public void setTeamExternalId(String teamExternalId) {
        this.teamExternalId = teamExternalId;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String getId() {
        return id;
    }
}
