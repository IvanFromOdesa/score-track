package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

public class APINbaPlayerLeaderboardDto {
    private String valueAvg;
    private String id;
    private String firstName;
    private String lastName;
    private String rank;
    private String imgUrl;
    private TeamShortDto team;

    public String getValueAvg() {
        return valueAvg;
    }

    public void setValueAvg(String valueAvg) {
        this.valueAvg = valueAvg;
    }

    public String getId() {
        return id;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public TeamShortDto getTeam() {
        return team;
    }

    public void setTeam(TeamShortDto team) {
        this.team = team;
    }

    public record TeamShortDto(String name, String logo, String code, String id) {}
}
