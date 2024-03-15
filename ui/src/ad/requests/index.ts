import axios from "axios";
import {BASE} from "../../common/requests/base";
import {SportApi} from "../../common/models/generic.model";

export const getApis = () => axios.get<SportApi[]>(`${BASE}/apis`)
    .then(res => res.data as SportApi[]);