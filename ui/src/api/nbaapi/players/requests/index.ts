import {baseAxios} from "../../../../common/base/requests/base";
import {ApiNbaPlayersLeaderboardData} from "../models/player.leaderboard.model";
import {PLAYERS_LEADERBOARD} from "../../common/req";

export const getLeaderboardPlayers = (season?: string, type?: string) => baseAxios.get<ApiNbaPlayersLeaderboardData>(`${PLAYERS_LEADERBOARD}?season=${season}&type=${type}`)
    .then(res => res.data as ApiNbaPlayersLeaderboardData)