package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaIdentifier;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Persist the team data.<br>
 * Reasons:<br>
 * 1. externalId is interchangeable<br>
 * 2. logoUrl is interchangeable<br>
 * 3. team can be erased from the league<br>
 * 4. other properties may also change<br>
 * It's better to fully save it rather than keep constants hardcoded.
 */
@Document(TeamData.COLLECTION_NAME)
public class TeamData extends APINbaIdentifier {
    public static final String COLLECTION_NAME = "teams";
    private String name;
    private String nickname;
    private String code;
    private String city;
    private String logo;
    private boolean allStar;
    private boolean nbaFranchise;
    private Map<String, League> leagues;
    /**
     * Season represents the year
     */
    private Map<Integer, TeamStats> statsBySeason;

    public TeamData() {
        leagues = new HashMap<>();
        statsBySeason = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isAllStar() {
        return allStar;
    }

    public void setAllStar(boolean allStar) {
        this.allStar = allStar;
    }

    public boolean isNbaFranchise() {
        return nbaFranchise;
    }

    public void setNbaFranchise(boolean nbaFranchise) {
        this.nbaFranchise = nbaFranchise;
    }

    public Map<String, League> getLeagues() {
        return leagues;
    }

    public void setLeagues(Map<String, League> leagues) {
        this.leagues = leagues;
    }

    public Map<Integer, TeamStats> getStatsBySeason() {
        return statsBySeason;
    }

    public void setStatsBySeason(Map<Integer, TeamStats> statsBySeason) {
        this.statsBySeason = statsBySeason;
    }

    public static TeamData withNbaFranchise() {
        TeamData teamData = new TeamData();
        teamData.setNbaFranchise(true);
        return teamData;
    }

    @Override
    public String toString() {
        return "TeamData{" +
                "externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", logo='" + logo + '\'' +
                ", allStar=" + allStar +
                ", nbaFranchise=" + nbaFranchise +
                ", league=" + leagues +
                '}';
    }

    public record League(String conference, String division) {
    }
}
