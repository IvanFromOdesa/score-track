package com.teamk.scoretrack.module.core.api.commons.base.controller;

import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import com.teamk.scoretrack.module.core.entities.SportType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.teamk.scoretrack.module.commons.base.controller.BaseRestController.BASE_URL;
import static com.teamk.scoretrack.module.core.api.commons.base.controller.ApiSearchController.SEARCH;

@RestController
@RequestMapping(BASE_URL + SEARCH)
public class ApiSearchController extends BaseRestController {
    public static final String SEARCH = "/search";
    private static final String SPORTS = "/sports";

    @GetMapping(SPORTS)
    public ResponseEntity<List<SportType>> sports(@RequestParam Optional<String> q) {
        return q.map(s -> ResponseEntity.ok(SportType.byName(s))).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
