package com.teamk.scoretrack.module.core.api.commons.sport_components.controller;

import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public abstract class ApiSportComponentRestController extends BaseRestController {
    protected static final String TEAMS_ENDPOINT = "/teams";
    protected static final String GAMES_ENDPOINT = "/games";
    protected static final String PLAYERS_ENDPOINT = "/players";

    protected abstract Page<?> getTeamsData(int page);
    protected abstract Optional<?> getPlayerDataById(String id);

    @GetMapping(TEAMS_ENDPOINT)
    public Page<?> getTeams(@RequestParam Optional<Integer> p) {
        return getTeamsData(p.orElse(0));
    }

    @GetMapping(PLAYERS_ENDPOINT + "/{id}")
    public ResponseEntity<?> getPlayerById(@PathVariable String id) {
        return getPlayerDataById(id).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }
}
