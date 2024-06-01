import {ApiNbaTeamModel} from "../models/team.model";
import {baseAxios} from "common/base/requests/base";
import {getTeamStatsPath, TEAMS} from "../../common/req";
import {ApiNbaTeamStatsData} from "../models/team.stats.model";

export const getTeams = (page: number) => baseAxios.get<ApiNbaTeamModel>(`${TEAMS}?p=${page}`)
    .then(res => res.data as ApiNbaTeamModel);

export const getTeamStats = (id: string, code: string) => baseAxios.get<ApiNbaTeamStatsData>(getTeamStatsPath(id, code))
    .then(res => res.data as ApiNbaTeamStatsData);