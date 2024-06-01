import {GamesModel} from "../models/base_game.model";
import {baseAxios} from "common/base/requests/base";
import {plainToInstance} from "class-transformer";

// TODO: pagination
export const getGames = (api: string) => baseAxios.get<GamesModel>(`${api}/games`)
    .then(res => plainToInstance(GamesModel, res.data as GamesModel));