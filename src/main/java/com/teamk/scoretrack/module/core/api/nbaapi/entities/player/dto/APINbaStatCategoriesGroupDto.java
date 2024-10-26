package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaStatCategoryDto;

public record APINbaStatCategoriesGroupDto(APINbaStatCategoryDto[] statCategories, String title) {
}
