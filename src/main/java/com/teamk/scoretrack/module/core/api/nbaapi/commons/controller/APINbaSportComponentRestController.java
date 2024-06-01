package com.teamk.scoretrack.module.core.api.nbaapi.commons.controller;

import com.teamk.scoretrack.module.core.api.commons.sport_components.controller.ApiSportComponentRestController;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaPlayersLeaderboardDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.PlayerDataDtoService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.dto.APINbaTeamStatsMapDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.TeamDataDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.teamk.scoretrack.module.commons.base.controller.BaseRestController.BASE_URL;
import static com.teamk.scoretrack.module.core.api.nbaapi.commons.controller.APINbaSportComponentRestController.API;

@RestController
@RequestMapping(BASE_URL + API)
public class APINbaSportComponentRestController extends ApiSportComponentRestController {
    public static final String API = "/nbaapi";
    public static final String STATS_ENDPOINT =  "/stats";
    private final TeamDataDtoService teamDataDtoService;
    private final PlayerDataDtoService playerDataDtoService;

    @Autowired
    public APINbaSportComponentRestController(TeamDataDtoService teamDataDtoService,
                                              PlayerDataDtoService playerDataDtoService) {
        this.teamDataDtoService = teamDataDtoService;
        this.playerDataDtoService = playerDataDtoService;
    }

    @Override
    protected Page<? extends SportComponentResponseDto> getTeamsData(int page) {
        return teamDataDtoService.getDtoPage(page, 10, "name");
    }

    @GetMapping(TEAMS_ENDPOINT + "/{id}" + STATS_ENDPOINT)
    public ResponseEntity<APINbaTeamStatsMapDto> getTeamStats(@PathVariable String id, @RequestParam String code) {
        return ResponseEntity.ok(teamDataDtoService.getTeamStats(id, code));
    }

    @GetMapping(PLAYERS_ENDPOINT + "/top")
    public ResponseEntity<APINbaPlayersLeaderboardDto> getLeaderboardPlayers(@RequestParam Optional<Integer> season,
                                                                             @RequestParam Optional<PlayerDataStatCategoryInfoHelper> type) {
        return ResponseEntity.ok(playerDataDtoService.getLeaderboardPlayers(type.orElse(PlayerDataStatCategoryInfoHelper.EFFICIENCY),
                season.orElse(SupportedSeasons.getOngoingSeason().getYear()), 10));
    }
}
