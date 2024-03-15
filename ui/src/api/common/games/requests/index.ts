import {GamesModel} from "../models/base_game.model";
import {BASE} from "common/requests/base";
import axios from "axios";
import {plainToInstance} from "class-transformer";

// TODO: pagination
export const getGames = (api: string) => axios.get<GamesModel>(`${BASE}${api}/games`)
    .then(res => plainToInstance(GamesModel, res.data as GamesModel));