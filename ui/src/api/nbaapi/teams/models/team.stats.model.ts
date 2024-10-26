import {ApiNbaStatsModel} from "../../common/models/stats.model";
import {ApiNbaSportComponentsHelpData} from "../../common/models/nbaapi.sc.metadata.model";
import {ApiNbaSeasonMap} from "../../common/models/season.model";
import {ApiNbaTeamInfoHelper} from "./team.model";

export interface ApiNbaTeamStatsModel extends ApiNbaStatsModel {
    games: number,
    fastBreakPoints: number,
    pointsInPaint: number,
    biggestLead: number,
    secondChancePoints: number,
    pointsOffTurnovers: number,
    longestRun: number
}

type ApiNbaTeamStatsMap = Record<string, ApiNbaTeamStatsModel>;

export interface ApiNbaTeamStatsData extends ApiNbaSeasonMap<ApiNbaTeamStatsModel> {
    infoHelper: ApiNbaTeamInfoHelper,
    description: string
}

/**
 * Represents avg league team stats by season
 */
export interface ApiNbaTeamsSportComponentsHelpData extends ApiNbaSportComponentsHelpData {
    data: ApiNbaTeamStatsMap;
}