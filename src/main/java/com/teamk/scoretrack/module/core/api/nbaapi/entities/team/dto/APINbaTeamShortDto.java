package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

public class APINbaTeamShortDto {
    private final String name, logo, code, id;

    public APINbaTeamShortDto(String name, String logo, String code, String id) {
        this.name = name;
        this.logo = logo;
        this.code = code;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return id;
    }
}
