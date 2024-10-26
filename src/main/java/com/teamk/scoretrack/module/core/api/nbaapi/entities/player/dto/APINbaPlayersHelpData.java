package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.dto;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaHelpData;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaStatCategoryDto;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.dto.SupportedSeasonsDto;

public record APINbaPlayersHelpData(SupportedSeasonsDto[] seasons, APINbaStatCategoryDto[] statCategories) implements APINbaHelpData {
}
