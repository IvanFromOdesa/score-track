package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamExtendedDto;

import java.time.LocalDate;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APINbaPlayerResponseDto {
    public String id;
    public String firstname;
    public String lastname;
    public BirthData birth;
    public CareerData nba;
    public HeightData height;
    public WeightData weight;
    public String college;
    public String affiliation;
    public Map<String, League> leagues;
    public Map<String, APINbaTeamExtendedDto> teamBySeason;
    public String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public BirthData getBirth() {
        return birth;
    }

    public void setBirth(BirthData birth) {
        this.birth = birth;
    }

    public CareerData getNba() {
        return nba;
    }

    public void setNba(CareerData nba) {
        this.nba = nba;
    }

    public HeightData getHeight() {
        return height;
    }

    public void setHeight(HeightData height) {
        this.height = height;
    }

    public WeightData getWeight() {
        return weight;
    }

    public void setWeight(WeightData weight) {
        this.weight = weight;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public Map<String, League> getLeagues() {
        return leagues;
    }

    public void setLeagues(Map<String, League> leagues) {
        this.leagues = leagues;
    }

    public Map<String, APINbaTeamExtendedDto> getTeamBySeason() {
        return teamBySeason;
    }

    public void setTeamBySeason(Map<String, APINbaTeamExtendedDto> teamBySeason) {
        this.teamBySeason = teamBySeason;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record League(int jersey, boolean active, APINbaPlayerPositionDto pos) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record BirthData(LocalDate date, String country) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CareerData(int start, int pro) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record HeightData(String feets, String inches, String meters) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record WeightData(String pounds, String kilograms) {}
}
