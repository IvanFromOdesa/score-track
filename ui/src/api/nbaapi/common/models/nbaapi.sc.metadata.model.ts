import {
    ApiSportComponentMetadataContainer,
    SportComponentKeys,
    SportComponentsHelpTextMap,
    SportComponentsMetadataMap
} from "../../../common/base/models/sport-components.metadata.model";
import {ValidateKeys} from "common/base/models/generic.model";
import {ApiNbaPlayersSportComponentsHelpData} from "../../players/models/player.sc.model";
import {ApiNbaTeamsSportComponentsHelpData} from "../../teams/models/team.stats.model";

export class ApiNbaSportComponentsMetadataContainer implements ApiSportComponentMetadataContainer<ApiNbaSportComponentsMetadataContainer> {
    helpData: ApiNbaSportComponentsHelpDataMap;
    components: SportComponentsMetadataMap;
    helpText: SportComponentsHelpTextMap;

    constructor(helpData: ApiNbaSportComponentsHelpDataMap,
                components: SportComponentsMetadataMap,
                helpText: SportComponentsHelpTextMap) {
        this.helpData = helpData;
        this.components = components;
        this.helpText = helpText;
    }

    getInstance(): ApiNbaSportComponentsMetadataContainer {
        return this;
    }
}

type ApiNbaSportComponentsHelpDataMap = ValidateKeys<SportComponentKeys, {
    teams: ApiNbaTeamsSportComponentsHelpData,
    players: ApiNbaPlayersSportComponentsHelpData,
    games: ApiNbaSportComponentsHelpData
}>

export interface ApiNbaSportComponentsHelpData {

}

