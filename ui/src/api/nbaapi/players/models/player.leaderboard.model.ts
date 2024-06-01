import {ApiNbaIdentifierModel} from "../../common/models/identifier.model";
import {ApiNbaTeamShortModel} from "../../teams/models/team.model";
import {UiHint} from "common/base/models/ui.helper.model";

export interface ApiNbaPlayerLeaderboardModel extends ApiNbaIdentifierModel {
    valueAvg: string;
    firstName: string;
    lastName: string;
    imgUrl: string;
    rank: string;
    team: ApiNbaTeamShortModel;
}

export interface ApiNbaPlayersLeaderboardData {
    data: ApiNbaPlayerLeaderboardModel[];
    hint: UiHint;
}