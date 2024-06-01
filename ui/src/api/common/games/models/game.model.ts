// Generic interface displaying different sports games

import {Bundle, CodeName, Sport, SportApi} from "common/base/models/generic.model";

export interface IGameModel {
    id: string;
    api: SportApi;
    sport: Sport;
    teams: Teams;
    status: GameStatus;
    winner: Team | null;
    country: Country;
    league: League;
    scores: Scores | null;
    date: string;
    timestamp: number;
    timezone: string;
    time: string;

    getTitle: () => string;
    getKeywords: () => string[];
    getTimestamp: () => string;
}

export interface IGamesModel {
    games: IGameModel[];
    updated: number;
    bundle: Bundle;

    getUpdated: (locale: string) => string;
}

export interface Teams {
    home: Team,
    away: Team
}

export interface Team {
    id: string,
    name: string,
    logo: string
}

export interface Country {
    id: string,
    name: string,
    code: string
}

export interface League {
    id: string,
    name: string,
    season: string,
    logo: string
}

type ScoreMap = {
    [period: string]: number;
}

export interface Scores {
    home: ScoreMap;
    away: ScoreMap;
}

export interface GameStatus extends CodeName {

}