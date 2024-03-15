package com.teamk.scoretrack.module.core.api.nbaapi.commons.controller;

import com.teamk.scoretrack.module.commons.base.page.RestPage;
import com.teamk.scoretrack.module.core.api.commons.base.controller.ApiRestController;
import com.teamk.scoretrack.module.core.api.commons.base.dto.ResponseDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.TeamDataDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.teamk.scoretrack.module.commons.base.controller.BaseRestController.BASE_URL;
import static com.teamk.scoretrack.module.core.api.nbaapi.commons.controller.APINbaRestController.API;

@RestController
@RequestMapping(BASE_URL + API)
public class APINbaRestController extends ApiRestController {
    public static final String API = "/nbaapi";
    private final TeamDataDtoService teamDataDtoService;

    @Autowired
    public APINbaRestController(TeamDataDtoService teamDataDtoService) {
        this.teamDataDtoService = teamDataDtoService;
    }

    @Override
    protected RestPage<? extends ResponseDto> getTeamsData(int page) {
        return teamDataDtoService.getDtoPage(page, 10, "name");
    }
}
