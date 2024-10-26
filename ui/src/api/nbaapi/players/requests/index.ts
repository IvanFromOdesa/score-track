import {baseAxios} from "../../../../common/base/requests/base";
import {ApiNbaPlayersLeaderboardData} from "../models/player.leaderboard.model";
import {getPlayerStatsPath, PLAYERS, PLAYERS_LEADERBOARD} from "../../common/req";
import {ApiNbaPlayerData} from "../models/player.model";
import {ApiNbaPlayerStatsData} from "../models/player.stats.model";

export const getLeaderboardPlayers = (season?: string, type?: string) => baseAxios.get<ApiNbaPlayersLeaderboardData>(`${PLAYERS_LEADERBOARD}?season=${season}&type=${type}`)
    .then(res => res.data as ApiNbaPlayersLeaderboardData);

export const getPlayerById = (id: string) => baseAxios.get<ApiNbaPlayerData>(`${PLAYERS}/${id}`)
    .then(res => res.data as ApiNbaPlayerData);

export const getPlayerAvgStatsPerSeason = (id: string) => baseAxios.get<ApiNbaPlayerStatsData>(getPlayerStatsPath(id))
    .then(res => res.data as ApiNbaPlayerStatsData);