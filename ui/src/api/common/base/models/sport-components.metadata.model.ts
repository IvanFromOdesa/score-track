import {Bundle, ValidateKeys} from "common/base/models/generic.model";
import {ApiCodeKeys} from "../../../apis";
import {ApiNbaSportComponentsMetadataContainer} from "../../../nbaapi/common/models/nbaapi.sc.metadata.model";
import {Type} from "class-transformer";

export interface SportComponentsMetadataModel {
    apiComponentsMetadata: ApiSportComponentMetadataMap;
}

export class SportComponentsMetadataModelImpl implements SportComponentsMetadataModel {
    @Type(() => ApiSportComponentMetadataMapImpl)
    apiComponentsMetadata: ApiSportComponentMetadataMap;

    constructor(apiComponentsMetadata: ApiSportComponentMetadataMap) {
        this.apiComponentsMetadata = apiComponentsMetadata;
    }
}

class ApiSportComponentMetadataMapImpl implements ApiSportComponentMetadataMap {
    "-1": null;
    @Type(() => ApiNbaSportComponentsMetadataContainer)
    "0": ApiNbaSportComponentsMetadataContainer;
}

type ApiSportComponentMetadataMap = ValidateKeys<ApiCodeKeys, {
    0: ApiNbaSportComponentsMetadataContainer;
    '-1': null;
}>

export interface ApiSportComponentMetadataContainer<T extends ApiSportComponentMetadataContainer<T>> {
    components: SportComponentsMetadataMap;
    helpText: SportComponentsHelpTextMap;

    getInstance(): T;
}

export type SportComponentKeys = 'teams' | 'players' | 'games';

export type SportComponentsMetadataMap = ValidateKeys<SportComponentKeys, {
    teams: SportComponentMetadata,
    players: SportComponentMetadata,
    games: SportComponentMetadata
}>

export type SportComponentsHelpTextMap = ValidateKeys<SportComponentKeys | 'commons', {
    teams: {bundle: Bundle},
    players: {bundle: Bundle},
    games: {bundle: Bundle},
    commons: {bundle: Bundle}
}>

export type SportComponentStatus = 'ACCESSIBLE' | 'DOWN';

export interface SportComponentMetadata {
    name: string;
    updated: number;
    status: SportComponentStatus;
    updateCount: number;
}