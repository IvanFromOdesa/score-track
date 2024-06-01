export const API_PATH = '/nbaapi';
export const TEAMS = API_PATH + '/teams';
export const PLAYERS = API_PATH + '/players';
export const PLAYERS_LEADERBOARD = PLAYERS + "/top";

const TEAM_STATS = TEAMS + '/:id/stats?code=*';

export function getTeamStatsPath(id: string, code: string) {
    return TEAM_STATS.replace(':id', id).replace('*', code);
}