package com.teamk.scoretrack.module.core.entities.sport_type;

import com.google.common.collect.BiMap;
import com.teamk.scoretrack.module.commons.base.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.teamk.scoretrack.module.commons.base.controller.BaseRestController.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/search/sports")
public class SportTypeSearchController extends BaseRestController {
    private final SportTypeNameReverseTranslationIndex sportTypeNameReverseTranslationIndex;

    @Autowired
    public SportTypeSearchController(SportTypeNameReverseTranslationIndex sportTypeNameReverseTranslationIndex) {
        this.sportTypeNameReverseTranslationIndex = sportTypeNameReverseTranslationIndex;
    }

    @GetMapping
    public ResponseEntity<List<SportTypeDto>> sports(@RequestParam String q) {
        return Optional.ofNullable(q).map(s -> {
            BiMap<String, String> codesByText = sportTypeNameReverseTranslationIndex.getCodesByText(s);
            List<SportTypeDto> list = SportType.byBundleKeys(codesByText.values().stream().toList()).stream()
                    .map(st -> new SportTypeDto(st, codesByText.inverse().get(st.getBundleKey()))).toList();
            return ResponseEntity.ok(list);
        }).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
