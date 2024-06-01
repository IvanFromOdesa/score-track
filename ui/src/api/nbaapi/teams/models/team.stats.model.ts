import {ApiNbaSeason, ApiNbaStatsModel} from "../../common/models/stats.model";
import {ApiNbaSportComponentsHelpData} from "../../common/models/nbaapi.sc.metadata.model";
import {UiHint} from "common/base/models/ui.helper.model";

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

export interface ApiNbaTeamStatsData {
    data: ApiNbaTeamStatsMap,
    seasons: ApiNbaSeason[],
    infoHelper: ApiNbaTeamInfoHelper,
    hint: UiHint
}

export interface ApiNbaTeamInfoHelper {
    code: string,
    arena: string,
    arenaName: string,
    src: string,
    color: string
}

/**
 * Represents avg league team stats by season
 */
export interface ApiNbaTeamsSportComponentsHelpData extends ApiNbaSportComponentsHelpData {
    data: ApiNbaTeamStatsMap;
}