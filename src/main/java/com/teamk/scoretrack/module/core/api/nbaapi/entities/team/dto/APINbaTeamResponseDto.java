package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

public class APINbaTeamResponseDto {
    public String id;
    public String name;
    public String nickname;
    public String code;
    public String city;
    public String logo;
    public boolean allStar;
    public boolean nbaFranchise;
    public LeagueWrapper leagues = new LeagueWrapper();

    public static class LeagueWrapper {
        public League standard = new League();
    }

    public static class League {
        public String conference;
        public String division;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LeagueWrapper getLeagues() {
        return leagues;
    }

    public void setLeagues(LeagueWrapper leagues) {
        this.leagues = leagues;
    }

    @Override
    public String toString() {
        return "APINbaTeamResponseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", logo='" + logo + '\'' +
                ", allStar=" + allStar +
                ", nbaFranchise=" + nbaFranchise +
                ", leagues=" + leagues +
                '}';
    }
}
