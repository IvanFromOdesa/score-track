import {Country, GameStatus, IGameModel, IGamesModel, League, Scores, Team, Teams} from "./game.model";
import {Type} from "class-transformer";
import {getDate, getDateTime} from "common/utils/common";
import {Bundle, SportApi, Sport} from "common/models/generic.model";

export class BaseGameModel implements IGameModel {
    api: SportApi;
    country: Country;
    date: string;
    id: string;
    league: League;
    scores: Scores | null;
    sport: Sport;
    status: GameStatus;
    teams: Teams;
    time: string;
    timestamp: number;
    timezone: string;
    winner: Team | null;

    constructor(api: SportApi,
                country: Country,
                date: string,
                id: string, league: League,
                scores: Scores,
                sport: Sport,
                status: GameStatus,
                teams: Teams,
                time: string,
                timestamp: number,
                timezone: string,
                winner: Team) {
        this.api = api;
        this.country = country;
        this.date = date;
        this.id = id;
        this.league = league;
        this.scores = scores;
        this.sport = sport;
        this.status = status;
        this.teams = teams;
        this.time = time;
        this.timestamp = timestamp;
        this.timezone = timezone;
        this.winner = winner;
    }

    getTitle = (): string => {
        const teams = this.teams;
        const scoresHome = this.scores ? '(' + this.scores.home['total'] + ')' : '';
        const scoresAway = this.scores ? '(' + this.scores.away['total'] + ')' : '';
        return `${teams.home.name} ${scoresHome} - ${teams.away.name} ${scoresAway}`;
    }

    getKeywords(): string[] {
        // TODO: add keywords coming from back end (e.g. 'OT', 'Game-winner' etc)
        return [this.api.name, this.sport.name, this.league.name];
    }

    getTimestamp(): string {
        return getDate(this.timestamp, "en-US");
    }
}

export class GamesModel implements IGamesModel {
    @Type(() => BaseGameModel)
    games: BaseGameModel[];
    updated: number;
    bundle: Bundle;

    constructor(games: BaseGameModel[], updated: number, bundle: Bundle) {
        this.games = games;
        this.updated = updated;
        this.bundle = bundle;
    }

    getUpdated(locale: string): string {
        return getDateTime(this.updated, locale);
    }
}