import {UiWrapperResponse} from "../../../../common/base/models/ui.helper.model";

export interface ApiNbaStatsModel {
    points: number,
    fgm: number,
    fga: number,
    fgp: string,
    ftm: number,
    fta: number,
    ftp: string,
    tpm: number,
    tpa: number,
    tpp: string,
    offReb: number,
    defReb: number,
    totReb: number,
    assists: number,
    pFouls: number,
    steals: number,
    turnovers: number,
    blocks: number,
    plusMinus: number,
}

export interface ApiNbaSeason extends UiWrapperResponse {

}

export interface ApiNbaStatCategory extends UiWrapperResponse {

}