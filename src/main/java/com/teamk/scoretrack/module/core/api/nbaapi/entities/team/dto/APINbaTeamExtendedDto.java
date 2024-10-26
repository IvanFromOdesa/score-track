package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto;

import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.domain.NbaTeamInfoHelper;

public class APINbaTeamExtendedDto extends APINbaTeamShortDto {
    private final NbaTeamInfoHelper infoHelper;

    public APINbaTeamExtendedDto(String name, String logo, String code, String id,
                                 NbaTeamInfoHelper infoHelper) {
        super(name, logo, code, id);
        this.infoHelper = infoHelper;
    }

    public NbaTeamInfoHelper getInfoHelper() {
        return infoHelper;
    }
}
