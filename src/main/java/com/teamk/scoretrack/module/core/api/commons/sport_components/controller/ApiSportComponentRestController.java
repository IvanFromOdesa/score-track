package com.teamk.scoretrack.module.core.api.commons.sport_components.controller;

import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public abstract class ApiSportComponentRestController extends BaseRestController {
    protected static final String TEAMS_ENDPOINT = "/teams";
    protected static final String GAMES_ENDPOINT = "/games";
    protected static final String PLAYERS_ENDPOINT = "/players";

    protected abstract Page<? extends SportComponentResponseDto> getTeamsData(int page);

    @GetMapping(TEAMS_ENDPOINT)
    public Page<? extends SportComponentResponseDto> getTeams(@RequestParam Optional<Integer> p) {
        return getTeamsData(p.orElse(0));
    }
}
