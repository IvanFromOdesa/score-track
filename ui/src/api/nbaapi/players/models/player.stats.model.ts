import {ApiNbaSportComponentsHelpData} from "../../common/models/nbaapi.sc.metadata.model";
import {ApiNbaSeason, ApiNbaStatCategory} from "../../common/models/stats.model";
import {UiWrapperResponse} from "../../../../common/base/models/ui.helper.model";

/**
 * Represents available seasons and stat categories for leaderboard.
 */
export interface ApiNbaPlayersSportComponentsHelpData extends ApiNbaSportComponentsHelpData {
    seasons: ApiNbaSeason[],
    statCategories: ApiNbaStatCategory[],
}