package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaIdentifier;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.TeamData;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(PlayerData.COLLECTION_NAME)
public class PlayerData extends APINbaIdentifier {
    public static final String COLLECTION_NAME = "players";
    private String firstName;
    private String lastName;
    private PlayerBirth birth;
    private CareerData nba;
    private HeightData height;
    private WeightData weight;
    private String college;
    private String affiliation;
    private Map<String, League> leagues;
    @DocumentReference(lazy = true)
    private Map<SupportedSeasons, TeamData> teamBySeason;
    private Map<SupportedSeasons, List<PlayerStats>> statsBySeason;

    public PlayerData() {
        leagues = new HashMap<>();
        teamBySeason = new HashMap<>();
        statsBySeason = new HashMap<>();
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

    public PlayerBirth getBirth() {
        return birth;
    }

    public void setBirth(PlayerBirth birth) {
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

    public Map<SupportedSeasons, TeamData> getTeamBySeason() {
        return teamBySeason;
    }

    public void setTeamBySeason(Map<SupportedSeasons, TeamData> teamBySeason) {
        this.teamBySeason = teamBySeason;
    }

    public Map<SupportedSeasons, List<PlayerStats>> getStatsBySeason() {
        return statsBySeason;
    }

    public void setStatsBySeason(Map<SupportedSeasons, List<PlayerStats>> statsBySeason) {
        this.statsBySeason = statsBySeason;
    }

    public record League(String jersey, boolean active, PlayerPosition position) {}

    public record WeightData(Double pounds, Double kilograms) {}

    public record HeightData(Integer feets, Integer inches, Double meters) {}

    public record CareerData(String start, int pro) {}

    public record PlayerBirth(LocalDate date, String country) {}
}
