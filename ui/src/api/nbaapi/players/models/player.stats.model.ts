import {ApiNbaStatsModel} from "../../common/models/stats.model";
import {UiWrapperResponse} from "common/base/models/ui.helper.model";
import {ApiNbaSeasonMap} from "../../common/models/season.model";
import {ValidateKeys} from "../../../../common/base/models/generic.model";
import {ApiNbaStatCategory} from "../../common/models/stat.category.model";

export interface ApiNbaPlayerStatsData extends ApiNbaSeasonMap<ApiNbaPlayerStatsModel> {
    groupedStatCategories: ApiNbaStatCategoriesGroupMap
}

export type ApiNbaStatCategoriesGroupKeys = 'main' | 'rebounds' | 'miscellaneous' | 'fieldGoals' | 'freeThrows' | 'threePoints';

interface ApiNbaStatCategoriesGroup {
    statCategories: ApiNbaStatCategory[],
    title: string
}

export type ApiNbaStatCategoriesGroupMap = ValidateKeys<ApiNbaStatCategoriesGroupKeys, {
    main: ApiNbaStatCategoriesGroup,
    rebounds: ApiNbaStatCategoriesGroup,
    miscellaneous: ApiNbaStatCategoriesGroup,
    fieldGoals: ApiNbaStatCategoriesGroup,
    freeThrows: ApiNbaStatCategoriesGroup,
    threePoints: ApiNbaStatCategoriesGroup
}>;

export interface ApiNbaPlayerStatsModel extends ApiNbaStatsModel {
    position: PlayerPosition;
}

interface PlayerPosition extends UiWrapperResponse<string> {

}