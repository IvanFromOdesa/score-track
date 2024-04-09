package com.teamk.scoretrack.module.core.api.nbaapi.commons.controller;

import com.teamk.scoretrack.module.commons.base.page.RestPage;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentResponseDto;
import com.teamk.scoretrack.module.core.api.commons.sport_components.controller.ApiSportComponentRestController;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsMapDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.TeamDataDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.teamk.scoretrack.module.commons.base.controller.BaseRestController.BASE_URL;
import static com.teamk.scoretrack.module.core.api.nbaapi.commons.controller.APINbaSportComponentRestController.API;

@RestController
@RequestMapping(BASE_URL + API)
public class APINbaSportComponentRestController extends ApiSportComponentRestController {
    public static final String API = "/nbaapi";
    public static final String STATS_ENDPOINT =  "/stats";
    private final TeamDataDtoService teamDataDtoService;

    @Autowired
    public APINbaSportComponentRestController(TeamDataDtoService teamDataDtoService) {
        this.teamDataDtoService = teamDataDtoService;
    }

    @Override
    protected RestPage<? extends SportComponentResponseDto> getTeamsData(int page) {
        return teamDataDtoService.getDtoPage(page, 10, "name");
    }

    @GetMapping(TEAMS_ENDPOINT + "/{id}" + STATS_ENDPOINT)
    public ResponseEntity<APINbaTeamStatsMapDto> getTeamStats(@PathVariable String id) {
        return ResponseEntity.ok(teamDataDtoService.getTeamStats(id));
    }
}
