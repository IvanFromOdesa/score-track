package com.teamk.scoretrack.module.core.api.commons.base.controller;

import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import com.teamk.scoretrack.module.core.api.commons.base.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public abstract class ApiRestController extends BaseRestController {
    public static final String TEAMS_ENDPOINT = "/teams";
    public static final String GAMES_ENDPOINT = "/games";
    public static final String PLAYERS_ENDPOINT = "/players";

    protected abstract Page<? extends ResponseDto> getTeamsData(int page);

    @GetMapping(TEAMS_ENDPOINT)
    public Page<? extends ResponseDto> getTeams(@RequestParam Optional<Integer> p) {
        return getTeamsData(p.orElse(0));
    }
}
