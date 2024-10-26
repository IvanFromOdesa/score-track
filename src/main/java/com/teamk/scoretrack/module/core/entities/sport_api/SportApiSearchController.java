package com.teamk.scoretrack.module.core.entities.sport_api;

import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.teamk.scoretrack.module.commons.base.controller.BaseRestController.BASE_URL;

@RequestMapping(BASE_URL + "/search/apis")
public class SportApiSearchController extends BaseRestController {
    @GetMapping
    public ResponseEntity<List<SportAPI>> supportedApis() {
        return new ResponseEntity<>(SportAPI.supported(), HttpStatus.OK);
    }
}
