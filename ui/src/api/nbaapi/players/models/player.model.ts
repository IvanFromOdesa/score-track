import {ApiNbaIdentifierModel} from "../../common/models/identifier.model";
import {UiWrapperResponse} from "../../../../common/base/models/ui.helper.model";
import {ValidateKeys} from "../../../../common/base/models/generic.model";
import {ApiNbaTeamExtendedModel} from "../../teams/models/team.model";
import {ApiNbaSeason} from "../../common/models/season.model";

export interface ApiNbaPlayerData {
    data: ApiNbaPlayerModel;
    seasons: ApiNbaSeason[];
}

export interface ApiNbaPlayerModel extends ApiNbaIdentifierModel {
    firstname: string;
    lastname: string;
    birth: BirthData;
    nba: CareerData;
    height: HeightData;
    weight: WeightData;
    college: string;
    affiliation: string;
    leagues: LeagueMap;
    teamBySeason: TeamBySeasonMap;
    imgUrl: string;
}

interface BirthData {
    date: string;
    country: string;
}

interface CareerData {
    start: number;
    pro: number;
}

interface HeightData {
    feets: string;
    inches: string;
    meters: string;
}

interface WeightData {
    pounds: string;
    kilograms: string;
}

type TeamBySeasonMap = Record<string, ApiNbaTeamExtendedModel>;

type LeagueMap = ValidateKeys<LeagueKeys, {
    africa: League;
    orlando: League;
    sacramento: League;
    standard: League;
    utah: League;
    vegas: League;
}>;

type LeagueKeys = 'africa' | 'orlando' | 'sacramento' | 'standard' | 'utah' | 'vegas';

interface League {
    jersey: number;
    active: boolean;
    pos: PlayerPosition;
}

interface PlayerPosition extends UiWrapperResponse<string> {

}