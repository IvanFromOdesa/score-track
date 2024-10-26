import {ApiNbaSportComponentsHelpData} from "../../common/models/nbaapi.sc.metadata.model";
import {ApiNbaSeason} from "../../common/models/season.model";
import {ApiNbaStatCategory} from "../../common/models/stat.category.model";

/**
 * Represents available seasons and stat categories for leaderboard.
 */
export interface ApiNbaPlayersSportComponentsHelpData extends ApiNbaSportComponentsHelpData {
    seasons: ApiNbaSeason[],
    statCategories: ApiNbaStatCategory[],
}