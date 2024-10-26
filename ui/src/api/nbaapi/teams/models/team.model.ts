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

export interface ApiNbaTeamExtendedModel extends ApiNbaTeamShortModel {
    infoHelper: ApiNbaTeamInfoHelper;
}

export interface ApiNbaTeamInfoHelper {
    code: string,
    arena: string,
    arenaName: string,
    src: string,
    colors: ApiNbaTeamColors;
}

export interface ApiNbaTeamColors {
    primary: string;
    secondary: string;
    tertiary: string;
}

type LeagueMap = Record<string, League>;

type League = {
    conference: string;
    division: string;
}

export interface ApiNbaTeamModel extends WithPagination<ApiNbaTeam> {

}