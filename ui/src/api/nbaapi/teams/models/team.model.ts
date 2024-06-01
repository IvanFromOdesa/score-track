import {WithPagination} from "../../../../common/pagination/pagination.model";
import {ApiNbaIdentifierModel} from "../../common/models/identifier.model";

export interface ApiNbaTeam extends ApiNbaTeamShortModel {
    nickname: string;
    city: string;
    allStar: boolean;
    nbaFranchise: boolean;
    leagues: LeagueMap;
}

export interface ApiNbaTeamShortModel extends ApiNbaIdentifierModel {
    name: string;
    logo: string;
    code: string;
}

type LeagueMap = Record<string, League>;

type League = {
    conference: string;
    division: string;
}

export interface ApiNbaTeamModel extends WithPagination<ApiNbaTeam> {

}