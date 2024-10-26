export const API_PATH = '/nbaapi';
export const TEAMS = API_PATH + '/teams';
export const PLAYERS = API_PATH + '/players';
export const PLAYERS_LEADERBOARD = PLAYERS + "/top";
export const PLAYERS_STATS = PLAYERS + "/:id/stats";

const TEAM_STATS = TEAMS + '/:id/stats?code=*';

export function getTeamStatsPath(id: string, code: string) {
    return TEAM_STATS.replace(':id', id).replace('*', code);
}

export function getPlayerStatsPath(id: string) {
    return PLAYERS_STATS.replace(':id', id);
}