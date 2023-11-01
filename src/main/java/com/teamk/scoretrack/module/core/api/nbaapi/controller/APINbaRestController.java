package com.teamk.scoretrack.module.core.api.nbaapi.controller;

import com.teamk.scoretrack.module.commons.controller.BaseRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.teamk.scoretrack.module.commons.controller.BaseRestController.BASE_URL;
import static com.teamk.scoretrack.module.core.api.nbaapi.controller.APINbaRestController.API;

@RestController
@RequestMapping(BASE_URL + API)
public class APINbaRestController extends BaseRestController {
    public static final String API = "/nbaapi";
    public static final String TEAMS_ENDPOINT = "/teams";
}
