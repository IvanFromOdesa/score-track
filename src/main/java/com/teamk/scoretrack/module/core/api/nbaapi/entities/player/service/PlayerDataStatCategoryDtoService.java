package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaStatCategoryDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto.APINbaStatCategoriesGroupDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.i18n.APINbaPlayerStatCategoriesTranslatorService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain.PlayerDataStatCategoryInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayerDataStatCategoryDtoService {
    private final APINbaPlayerStatCategoriesTranslatorService infoHelperTranslatorService;

    @Autowired
    public PlayerDataStatCategoryDtoService(APINbaPlayerStatCategoriesTranslatorService infoHelperTranslatorService) {
        this.infoHelperTranslatorService = infoHelperTranslatorService;
    }

    public APINbaStatCategoryDto[] getLeaderboardStatCategoryDtoArray() {
        return getStatCategoryDtoArray(PlayerDataStatCategoryInfoHelper.getLeaderboardCategories());
    }

    public Map<String, APINbaStatCategoriesGroupDto> getGroupedStatBoardCategoriesDtoMap() {
        return PlayerDataStatCategoryInfoHelper.getGroupedStatBoardCategories()
                .entrySet().stream().collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> new APINbaStatCategoriesGroupDto(
                                getStatCategoryDtoArray(e.getValue()),
                                infoHelperTranslatorService.getMessage(e.getKey().getUiHintTitleCode()))
                        )
                );
    }

    private APINbaStatCategoryDto[] getStatCategoryDtoArray(PlayerDataStatCategoryInfoHelper[] leaderboardCategories) {
        return Arrays.stream(leaderboardCategories)
                .map(p -> new APINbaStatCategoryDto(p.getCategoryName(), infoHelperTranslatorService.getMessage(p.getUiHintDropdownTitleCode(), "stat_categories")))
                .toArray(APINbaStatCategoryDto[]::new);
    }
}
