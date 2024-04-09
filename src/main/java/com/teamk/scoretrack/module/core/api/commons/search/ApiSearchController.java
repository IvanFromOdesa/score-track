package com.teamk.scoretrack.module.core.api.commons.search;

import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import com.teamk.scoretrack.module.core.entities.SportType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.teamk.scoretrack.module.commons.base.controller.BaseRestController.BASE_URL;
import static com.teamk.scoretrack.module.core.api.commons.search.ApiSearchController.SEARCH;

@RestController
@RequestMapping(BASE_URL + SEARCH)
public class ApiSearchController extends BaseRestController {
    public static final String SEARCH = "/search";
    private static final String SPORTS = "/sports";
    public static final String SUPPORTED_APIS = "/apis";

    @GetMapping(SPORTS)
    public ResponseEntity<List<SportType>> sports(@RequestParam Optional<String> q) {
        return q.map(s -> ResponseEntity.ok(SportType.byName(s))).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(SUPPORTED_APIS)
    public ResponseEntity<List<SportAPI>> supportedApis() {
        return new ResponseEntity<>(SportAPI.supported(), HttpStatus.OK);
    }
}
